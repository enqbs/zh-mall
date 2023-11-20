package com.enqbs.app.service.coupon;

import com.enqbs.app.pojo.vo.CouponVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.generator.dao.CouponMapper;
import com.enqbs.generator.pojo.Coupon;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponMapper couponMapper;

    @Override
    public List<CouponVO> getCouponVOList(Set<Integer> couponIdSet) {
        return couponMapper.selectListByCouponIdSet(couponIdSet).stream().map(this::coupon2CouponVO).collect(Collectors.toList());
    }

    @Override
    public CouponVO getCouponVO(Integer couponId) {
        CouponVO couponVO = new CouponVO();
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);

        if (ObjectUtils.isEmpty(coupon) || Constants.IS_DELETE.equals(coupon.getDeleteStatus())) {
            return couponVO;
        }

        return coupon2CouponVO(coupon);
    }

    @Override
    public int updateCoupon(Integer couponId, Integer quantity) {
        return couponMapper.updateByDeductQuantity(couponId, quantity);
    }

    private CouponVO coupon2CouponVO(Coupon coupon) {
        CouponVO couponVO = new CouponVO();
        BeanUtils.copyProperties(coupon, couponVO);
        return couponVO;
    }

}