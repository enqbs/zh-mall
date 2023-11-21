package com.enqbs.app.service.coupon;

import com.enqbs.app.convert.CouponConvert;
import com.enqbs.app.pojo.vo.CouponVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.CouponMapper;
import com.enqbs.generator.pojo.Coupon;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponMapper couponMapper;

    @Resource
    private CouponConvert couponConvert;

    @Override
    public List<CouponVO> getCouponVOList(Set<Integer> couponIdSet) {
        return couponMapper.selectListByCouponIdSet(couponIdSet).stream()
                .map(e -> couponConvert.coupon2CouponVO(e)).collect(Collectors.toList());
    }

    @Override
    public PageUtil<CouponVO> getCouponVOList(SortEnum sortEnum, Integer pageNum, Integer pageSize) {
        PageUtil<CouponVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<Coupon> couponList = couponMapper.selectListByParam(null, null, null, null,
                Constants.IS_NOT_DELETE, sortEnum.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(couponList)) {
            pageUtil.setTotal(total);
            pageUtil.setList(Collections.emptyList());
            return pageUtil;
        }

        total = couponMapper.countByParam(null, null, null, null, Constants.IS_NOT_DELETE);
        List<CouponVO> couponVOList = couponList.stream().map(e -> couponConvert.coupon2CouponVO(e)).collect(Collectors.toList());
        pageUtil.setTotal(total);
        pageUtil.setList(couponVOList);
        return pageUtil;
    }

    @Override
    public CouponVO getCouponVO(Integer couponId) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);

        if (ObjectUtils.isEmpty(coupon) || Constants.IS_DELETE.equals(coupon.getDeleteStatus())) {
            return new CouponVO();
        }

        return couponConvert.coupon2CouponVO(coupon);
    }

    @Override
    public int update(Integer couponId, Integer quantity) {
        return couponMapper.updateByDeductQuantity(couponId, quantity);
    }

}
