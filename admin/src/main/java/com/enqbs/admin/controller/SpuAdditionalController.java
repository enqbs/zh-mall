package com.enqbs.admin.controller;

import com.enqbs.admin.form.ProductOverviewForm;
import com.enqbs.admin.form.ProductSlideForm;
import com.enqbs.admin.form.ProductSpecForm;
import com.enqbs.admin.service.product.SpuOverviewService;
import com.enqbs.admin.service.product.SpuSlideService;
import com.enqbs.admin.service.product.SpuSpecService;
import com.enqbs.admin.vo.SpuOverviewVO;
import com.enqbs.admin.vo.SpuSlideVO;
import com.enqbs.admin.vo.SpuSpecVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class SpuAdditionalController {

    @Resource
    private SpuSlideService spuSlideService;

    @Resource
    private SpuOverviewService spuOverviewService;

    @Resource
    private SpuSpecService spuSpecService;

    @GetMapping("/overview/{spuId}")
    public R<SpuOverviewVO> productOverview(@PathVariable Integer spuId) {
        SpuOverviewVO spuOverviewInfo = spuOverviewService.getSpuOverviewVO(spuId);
        return R.ok(spuOverviewInfo);
    }

    @PostMapping("/overview")
    @PreAuthorize("hasAuthority('PRODUCT:ADD')")
    public R<Void> addProductOverview(@Valid @RequestBody ProductOverviewForm form) {
        int row = spuOverviewService.insert(form);

        if (row <= 0) {
            throw new ServiceException("商品概述页保存失败");
        }

        return R.ok("商品概述页保存成功");
    }

    @PutMapping("/overview")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> updateProductOverview(@Valid @RequestBody ProductOverviewForm form) {
        int row = spuOverviewService.update(form);

        if (row <= 0) {
            throw new ServiceException("商品概述页修改失败");
        }

        return R.ok("商品概述页修改成功");
    }

    @DeleteMapping("/overview/{spuId}")
    @PreAuthorize("hasAuthority('PRODUCT:DELETE')")
    public R<Void> deleteProductOverview(@PathVariable Integer spuId) {
        int row = spuOverviewService.delete(spuId);

        if (row <= 0) {
            throw new ServiceException("商品概述页删除失败");
        }

        return R.ok("商品概述页删除成功");
    }

    @GetMapping("/spec/{spuId}")
    public R<SpuSpecVO> productSpec(@PathVariable Integer spuId) {
        SpuSpecVO spuSpecInfo = spuSpecService.getSpuSpecVO(spuId);
        return R.ok(spuSpecInfo);
    }

    @PostMapping("/spec")
    @PreAuthorize("hasAuthority('PRODUCT:ADD')")
    public R<Void> addProductSpec(@Valid @RequestBody ProductSpecForm form) {
        int row = spuSpecService.insert(form);

        if (row <= 0) {
            throw new ServiceException("商品参数页保存失败");
        }

        return R.ok("商品参数页保存成功");
    }

    @PutMapping("/spec")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> updateProductSpec(@Valid @RequestBody ProductSpecForm form) {
        int row = spuSpecService.update(form);

        if (row <= 0) {
            throw new ServiceException("商品参数页修改失败");
        }

        return R.ok("商品参数页修改成功");
    }

    @DeleteMapping("/spec/{spuId}")
    @PreAuthorize("hasAuthority('PRODUCT:DELETE')")
    public R<Void> deleteProductSpec(@PathVariable Integer spuId) {
        int row = spuSpecService.delete(spuId);

        if (row <= 0) {
            throw new ServiceException("商品参数页删除失败");
        }

        return R.ok("商品参数页删除成功");
    }

    @GetMapping("/slide/{spuId}")
    public R<SpuSlideVO> productSlide(@PathVariable Integer spuId) {
        SpuSlideVO spuSlideInfo = spuSlideService.getSpuSlideVO(spuId);
        return R.ok(spuSlideInfo);
    }

    @PostMapping("/slide")
    @PreAuthorize("hasAuthority('PRODUCT:ADD')")
    public R<Void> addProductSlide(@Valid @RequestBody ProductSlideForm form) {
        int row = spuSlideService.insert(form);

        if (row <= 0) {
            throw new ServiceException("商品轮播图保存失败");
        }

        return R.ok("商品轮播图保存成功");
    }

    @PutMapping("/slide")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> updateProductSlide(@Valid @RequestBody ProductSlideForm form) {
        int row = spuSlideService.update(form);

        if (row <= 0) {
            throw new ServiceException("商品轮播图修改失败");
        }

        return R.ok("商品轮播图修改成功");
    }

    @DeleteMapping("/slide/{spuId}")
    @PreAuthorize("hasAuthority('PRODUCT:DELETE')")
    public R<Void> deleteProductSlide(@PathVariable Integer spuId) {
        int row = spuSlideService.delete(spuId);

        if (row <= 0) {
            throw new ServiceException("商品轮播图删除失败");
        }

        return R.ok("商品轮播图删除成功");
    }

}
