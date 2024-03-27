package com.enqbs.admin.controller;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.ProductForm;
import com.enqbs.admin.service.product.SpuService;
import com.enqbs.admin.vo.ProductVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class SpuController {

    @Resource
    private SpuService spuService;

    @GetMapping("/list")
    public R<PageUtil<ProductVO>> productListPage(@RequestParam(required = false) Integer categoryId,
                                                  @RequestParam(required = false) Integer saleableStatus,
                                                  @RequestParam(required = false) Integer newStatus,
                                                  @RequestParam(required = false) Integer recommendStatus,
                                                  @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                                  @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<ProductVO> productVOListPage = spuService.productVOListPage(categoryId, saleableStatus, newStatus, recommendStatus,
                deleteStatus, sort, pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(productVOListPage);
    }

    @GetMapping("/{productId}")
    public R<ProductVO> productDetail(@PathVariable Integer productId) {
        ProductVO productInfo = spuService.getProductVO(productId);
        return R.ok(productInfo);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCT:ADD')")
    public R<Void> addProduct(@Valid @RequestBody ProductForm form) {
        int row = spuService.insert(form);

        if (row <= 0) {
            throw new ServiceException("商品保存失败");
        }

        return R.ok("商品保存成功");
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductForm form) {
        int row = spuService.update(productId, form);

        if (row <= 0) {
            throw new ServiceException("商品修改失败");
        }

        return R.ok("商品修改成功");
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('PRODUCT:DELETE')")
    public R<Void> deleteProduct(@PathVariable Integer productId) {
        int row = spuService.delete(productId);

        if (row <= 0) {
            throw new ServiceException("商品删除失败");
        }

        return R.ok("商品删除成功");
    }

    @PutMapping("/shelves/{productId}")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> productOnAndOffTheShelves(@PathVariable Integer productId) {
        int row = spuService.spuOnAndOffTheShelves(productId);

        if (row <= 0) {
            throw new ServiceException("修改失败");
        }

        return R.ok("修改成功");
    }

}
