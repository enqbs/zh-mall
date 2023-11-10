package com.enqbs.admin.controller;

import com.enqbs.admin.form.ProductForm;
import com.enqbs.admin.form.SkuForm;
import com.enqbs.admin.service.product.ProductService;
import com.enqbs.admin.vo.ProductVO;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class ProductController {

    @Resource
    private ProductService productService;

    @GetMapping("/product/list")
    public R<PageUtil<ProductVO>> productList(@RequestParam(required = false) Integer categoryId,
                                              @RequestParam(required = false) Integer saleableStatus,
                                              @RequestParam(required = false) Integer newStatus,
                                              @RequestParam(required = false) Integer recommendStatus,
                                              @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                              @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize <= 0) {
            pageSize = 10;
        }

        PageUtil<ProductVO> pageProductVOList = productService.getProductVOList(categoryId, saleableStatus, newStatus, recommendStatus, deleteStatus, sort, pageNum, pageSize);
        return R.ok(pageProductVOList);
    }

    @GetMapping("/product/{productId}")
    public R<ProductVO> productDetail(@PathVariable Integer productId) {
        ProductVO productVO = productService.getProductVO(productId);
        return R.ok(productVO);
    }

    @PostMapping("/product")
    @PreAuthorize("hasAuthority('PRODUCT:ADD')")
    public R<Void> productAdd(@Valid @RequestBody ProductForm form) {
        int row = productService.insertProduct(form);

        if (row <= 0) {
            throw new ServiceException("商品保存失败");
        }

        return R.ok("商品保存成功");
    }

    @PutMapping("/product/{productId}")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> productUpdate(@PathVariable Integer productId, @Valid @RequestBody ProductForm form) {
        int row = productService.updateProduct(productId, form);

        if (row <= 0) {
            throw new ServiceException("商品更新失败");
        }

        return R.ok("商品更新成功");
    }

    @DeleteMapping("/product/{productId}")
    @PreAuthorize("hasAuthority('PRODUCT:DELETE')")
    public R<Void> productDelete(@PathVariable Integer productId) {
        int row = productService.deleteProduct(productId);

        if (row <= 0) {
            throw new ServiceException("商品删除失败");
        }

        return R.ok("商品删除成功");
    }

    @PostMapping("/product/sku")
    @PreAuthorize("hasAuthority('PRODUCT:ADD')")
    public R<Void> skuAdd(@Valid @RequestBody SkuForm form) {
        productService.insertSku(form);
        return R.ok("商品规格保存成功");
    }

    @PutMapping("/product/sku/{skuId}")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> skuUpdate(@PathVariable Integer skuId, @Valid @RequestBody SkuForm form) {
        productService.updateSku(skuId, form);
        return R.ok("商品规格更行成功");
    }

    @DeleteMapping("/product/sku/{skuId}")
    @PreAuthorize("hasAuthority('PRODUCT:DELETE')")
    public R<Void> skuDelete(@PathVariable Integer skuId) {
        int row = productService.deleteSku(skuId);

        if (row <= 0) {
            throw new ServiceException("商品规格删除失败");
        }

        return R.ok("商品规格删除成功");
    }

}
