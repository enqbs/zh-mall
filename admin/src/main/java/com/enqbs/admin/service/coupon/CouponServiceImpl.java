package com.enqbs.admin.service.coupon;

import com.enqbs.admin.convert.CouponConvert;
import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.CouponForm;
import com.enqbs.admin.vo.CouponVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.CouponMapper;
import com.enqbs.generator.pojo.Coupon;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponMapper couponMapper;

    @Resource
    private CouponConvert couponConvert;

    @Override
    public PageUtil<CouponVO> getCouponVOList(Integer productId, Date startDate, Date endDate, Integer status,
                                              Integer deleteStatus, SortEnum sort, Integer pageNum, Integer pageSize) {
        PageUtil<CouponVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        List<Coupon> couponList = couponMapper.selectListByParam(productId, startDate, endDate, status,
                deleteStatus, sort.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(couponList)) {
            return pageUtil;
        }

        Long total = couponMapper.countByParam(productId, startDate, endDate, status, deleteStatus);
        List<CouponVO> couponVOList = couponList.stream().map(e -> couponConvert.coupon2CouponVO(e)).collect(Collectors.toList());
        pageUtil.setTotal(total);
        pageUtil.setList(couponVOList);
        return pageUtil;
    }

    @Override
    public CouponVO getCouponVO(Integer couponId) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        return ObjectUtils.isEmpty(coupon) ? new CouponVO() : couponConvert.coupon2CouponVO(coupon);
    }

    @Override
    public int insert(CouponForm form) {
        Coupon coupon = couponConvert.couponForm2Coupon(form);
        return couponMapper.insertSelective(coupon);
    }

    @Override
    public int update(Integer couponId, CouponForm form) {
        Coupon coupon = couponConvert.couponForm2Coupon(form);
        coupon.setId(couponId);
        return couponMapper.updateByPrimaryKeySelective(coupon);
    }

    @Override
    public int delete(Integer couponId) {
        Coupon coupon = new Coupon();
        coupon.setId(couponId);
        coupon.setStatus(Constants.COUPON_INVALID);
        coupon.setDeleteStatus(Constants.IS_DELETE);
        return couponMapper.updateByPrimaryKeySelective(coupon);
    }

}