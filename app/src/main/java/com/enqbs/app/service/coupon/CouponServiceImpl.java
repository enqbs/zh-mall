package com.enqbs.app.service.coupon;

import com.enqbs.app.convert.CouponConvert;
import com.enqbs.app.pojo.vo.CouponVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.CouponMapper;
import com.enqbs.generator.pojo.Coupon;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponMapper couponMapper;

    @Resource
    private CouponConvert couponConvert;

    @Override
    public PageUtil<CouponVO> couponVOPage(SortEnum sort, Integer pageNum, Integer pageSize) {
        PageUtil<CouponVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        List<Coupon> couponList = couponMapper.selectListByParam(null, null, null, null,
                Constants.IS_NOT_DELETE, sort.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(couponList)) {
            return pageUtil;
        }

        Long total = couponMapper.countByParam(null, null, null, null, Constants.IS_NOT_DELETE);
        pageUtil.setTotal(total);
        pageUtil.setList(couponList.stream().map(c -> couponConvert.coupon2CouponVO(c)).toList());
        return pageUtil;
    }

    @Override
    public List<CouponVO> getCouponVOList(Set<Integer> couponIdSet) {
        return couponMapper.selectListByIdSet(couponIdSet).stream().map(c -> couponConvert.coupon2CouponVO(c)).toList();
    }

    @Override
    public CouponVO getCouponVO(Integer couponId) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        return ObjectUtils.isEmpty(coupon) || Constants.IS_DELETE.equals(coupon.getDeleteStatus()) ?
                new CouponVO() : couponConvert.coupon2CouponVO(coupon);
    }

    @Override
    public int update(Integer couponId, Integer quantity) {
        return couponMapper.updateByDeductQuantity(couponId, quantity);
    }

}
