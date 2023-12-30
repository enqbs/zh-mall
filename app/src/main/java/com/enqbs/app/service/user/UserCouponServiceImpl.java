package com.enqbs.app.service.user;

import com.enqbs.app.convert.UserConvert;
import com.enqbs.app.pojo.vo.CouponVO;
import com.enqbs.app.pojo.vo.UserCouponVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.service.coupon.CouponService;
import com.enqbs.common.constant.Constants;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.UserCouponMapper;
import com.enqbs.generator.pojo.UserCoupon;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserCouponServiceImpl implements UserCouponService {

    @Resource
    private UserCouponMapper userCouponMapper;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private UserService userService;
    @Resource
    private CouponService couponService;
    @Resource
    private UserConvert userConvert;

    @Override
    public PageUtil<UserCouponVO> couponVOPage(Integer status, SortEnum sort, Integer pageNum, Integer pageSize) {
        PageUtil<UserCouponVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        List<UserCoupon> couponList = userCouponMapper.selectListByParam(userInfoVO.getUserId(), status,
                Constants.IS_NOT_DELETE, sort.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(couponList)) {
            return pageUtil;
        }

        Long total = userCouponMapper.countByParam(userInfoVO.getUserId(), status, Constants.IS_NOT_DELETE);
        List<UserCouponVO> couponVOList = couponList.stream().map(c -> userConvert.coupon2CouponVO(c)).toList();
        handleUserCouponVO(couponVOList);
        pageUtil.setTotal(total);
        pageUtil.setList(couponVOList);
        return pageUtil;
    }

    @Override
    public List<UserCouponVO> getCouponVOList() {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        List<UserCoupon> couponList = userCouponMapper.selectListByParam(userInfoVO.getUserId(), Constants.COUPON_UNUSED,
                Constants.IS_NOT_DELETE, SortEnum.DESC.getSortType(), null, null);

        if (CollectionUtils.isEmpty(couponList)) {
            return Collections.emptyList();
        }

        List<UserCouponVO> couponVOList = couponList.stream().map(c -> userConvert.coupon2CouponVO(c)).toList();
        handleUserCouponVO(couponVOList);
        return couponVOList;
    }

    @Override
    public UserCouponVO getCouponVO(Integer couponId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        UserCoupon coupon = userCouponMapper.selectByCouponIdAndUserId(couponId, userInfoVO.getUserId());

        if (ObjectUtils.isEmpty(coupon)) {
            throw new ServiceException("优惠券不存在");
        }

        UserCouponVO couponVO = userConvert.coupon2CouponVO(coupon);
        couponVO.setCoupon(couponService.getCouponVO(coupon.getCouponId()));
        return couponVO;
    }

    @Override
    public void add(Integer couponId) {
        int quantity = 1;
        CouponVO couponVO = couponService.getCouponVO(couponId);

        if (new Date().compareTo(couponVO.getEndDate()) > 0) {
            throw new ServiceException("优惠券活动已结束");
        }

        if (couponVO.getQuantity() < quantity) {
            throw new ServiceException("优惠券数量不足");
        }

        UserInfoVO userInfoVO = userService.getUserInfoVO();
        String redisKey = String.format(Constants.USER_COUPON_LOCK, userInfoVO.getUserId());
        RLock lock = redissonClient.getLock(redisKey);      // 锁粒度为 userId,不同 userId 相当于无锁状态
        lock.lock();

        try {
            UserCouponService userCouponServiceProxy = (UserCouponService) AopContext.currentProxy();
            userCouponServiceProxy.insert(couponId, userInfoVO.getUserId(), quantity);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Integer couponId, Integer userId, Integer quantity) {
        Integer exist = userCouponMapper.existByCouponIdAndUserId(couponId, userId);

        if (ObjectUtils.isNotEmpty(exist)) {
            throw new ServiceException("您已领取过该优惠券了,请勿重复领取");
        }

        UserCoupon coupon = new UserCoupon();
        coupon.setCouponId(couponId);
        coupon.setUserId(userId);
        coupon.setQuantity(quantity);
        int updateCouponRow = couponService.update(couponId, quantity);
        int insertUserCouponRow = userCouponMapper.insertSelective(coupon);

        if (updateCouponRow <= 0 || insertUserCouponRow <= 0) {
            throw new ServiceException("优惠券领取失败");
        }
    }

    @Override
    public void deduct(Long orderNo, Integer couponId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        int row = userCouponMapper.updateStatusByCouponIdAndUserId(couponId, userInfoVO.getUserId(), Constants.COUPON_USED);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",优惠券扣除失败");
        }

        log.info("订单号:'{}',优惠券扣除成功.", orderNo);
    }

    @Override
    public void rollback(Long orderNo, Integer couponId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        int row = userCouponMapper.updateStatusByCouponIdAndUserId(couponId, userInfoVO.getUserId(), Constants.COUPON_UNUSED);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",优惠券回退失败");
        }

        log.info("订单号:'{}',优惠券回退成功.", orderNo);
    }

    @Override
    public int delete(Integer couponId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        UserCoupon coupon = userCouponMapper.selectByCouponIdAndUserId(couponId, userInfoVO.getUserId());

        if (ObjectUtils.isEmpty(coupon)) {
            throw new ServiceException("优惠券不存在");
        }

        coupon.setDeleteStatus(Constants.IS_DELETE);
        return userCouponMapper.updateByPrimaryKeySelective(coupon);
    }

    private void handleUserCouponVO(List<UserCouponVO> couponVOList) {
        Set<Integer> couponIdSet = couponVOList.stream().map(UserCouponVO::getCouponId).collect(Collectors.toSet());
        Map<Integer, CouponVO> couponVOMap = couponService.getCouponVOList(couponIdSet).stream()
                .collect(Collectors.toMap(CouponVO::getId, v -> v));
        couponVOList.forEach(cvo -> cvo.setCoupon(couponVOMap.get(cvo.getCouponId())));
    }

}
