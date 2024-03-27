package com.enqbs.app.service.coupon;

import com.enqbs.app.convert.CouponConvert;
import com.enqbs.app.pojo.vo.CouponVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.app.enums.SortEnum;
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
    public PageUtil<CouponVO> couponVOListPage(SortEnum sort, Integer pageNum, Integer pageSize) {
        Long total = couponMapper.countByParam(null, null, null, null, Constants.IS_NOT_DELETE);
        List<Coupon> couponList = couponMapper.selectListByParam(null, null, null, null, Constants.IS_NOT_DELETE, sort.getSortType(), pageNum, pageSize);
        PageUtil<CouponVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        pageUtil.setTotal(total);
        pageUtil.setList(couponList.stream().map(c -> couponConvert.coupon2CouponVO(c)).collect(Collectors.toList()));
        return pageUtil;
    }

    @Override
    public List<CouponVO> getCouponVOList(Set<Integer> couponIdSet) {
        return CollectionUtils.isEmpty(couponIdSet) ? Collections.emptyList() : couponMapper.selectListByCouponIdSet(couponIdSet).stream().map(c -> couponConvert.coupon2CouponVO(c)).collect(Collectors.toList());
    }

    @Override
    public CouponVO getCouponVO(Integer couponId) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        return ObjectUtils.isEmpty(coupon) || Constants.IS_DELETE.equals(coupon.getDeleteStatus()) ? null : couponConvert.coupon2CouponVO(coupon);
    }

    @Override
    public int update(Integer couponId, Integer quantity) {
        return couponMapper.updateByDeductQuantity(couponId, quantity);
    }

}
