package com.enqbs.admin.controller;

import com.enqbs.admin.form.ProductCategoryForm;
import com.enqbs.admin.form.ProductForm;
import com.enqbs.admin.form.SkuForm;
import com.enqbs.admin.service.product.ProductCategoryService;
import com.enqbs.admin.service.product.ProductService;
import com.enqbs.admin.service.product.SkuService;
import com.enqbs.admin.vo.ProductCategoryVO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductCategoryService productCategoryService;

    @Resource
    private ProductService productService;

    @Resource
    private SkuService skuService;

    @GetMapping("/category/list")
    public R<PageUtil<ProductCategoryVO>> categoryList(@RequestParam(required = false) Integer parentId,
                                                       @RequestParam(required = false) Integer naviStatus,
                                                       @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<ProductCategoryVO> pageCategoryList = productCategoryService.getProductCategoryVOList(parentId, naviStatus,
                deleteStatus, pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(pageCategoryList);
    }

    @GetMapping("/category/{categoryId}")
    public R<ProductCategoryVO> categoryDetail(@PathVariable Integer categoryId) {
        ProductCategoryVO categoryInfo = productCategoryService.getProductCategoryVO(categoryId);
        return R.ok(categoryInfo);
    }

    @PostMapping("/category")
    @PreAuthorize("hasAuthority('PRODUCT:ADD')")
    public R<Void> addCategory(@Valid @RequestBody ProductCategoryForm form) {
        int row = productCategoryService.insert(form);

        if (row <= 0) {
            throw new ServiceException("商品分类保存失败");
        }

        return R.ok("商品分类保存成功");
    }

    @PutMapping("/category/{categoryId}")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> updateCategory(@PathVariable Integer categoryId, @Valid @RequestBody ProductCategoryForm form) {
        int row = productCategoryService.update(categoryId, form);

        if (row <= 0) {
            throw new ServiceException("商品分类更新失败");
        }

        return R.ok("商品分类更新成功");
    }

    @DeleteMapping("/category/{categoryId}")
    @PreAuthorize("hasAuthority('PRODUCT:DELETE')")
    public R<Void> deleteCategory(@PathVariable Integer categoryId) {
        int row = productCategoryService.delete(categoryId);

        if (row <= 0) {
            throw new ServiceException("商品分类删除失败");
        }

        return R.ok("商品分类删除成功");
    }

    @GetMapping("/list")
    public R<PageUtil<ProductVO>> productList(@RequestParam(required = false) Integer categoryId,
                                              @RequestParam(required = false) Integer saleableStatus,
                                              @RequestParam(required = false) Integer newStatus,
                                              @RequestParam(required = false) Integer recommendStatus,
                                              @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                              @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<ProductVO> pageProductList = productService.getProductVOList(categoryId, saleableStatus, newStatus,
                recommendStatus, deleteStatus, sort, pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(pageProductList);
    }

    @GetMapping("/{productId}")
    public R<ProductVO> productDetail(@PathVariable Integer productId) {
        ProductVO productInfo = productService.getProductVO(productId);
        return R.ok(productInfo);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCT:ADD')")
    public R<Void> addProduct(@Valid @RequestBody ProductForm form) {
        int row = productService.insert(form);

        if (row <= 0) {
            throw new ServiceException("商品保存失败");
        }

        return R.ok("商品保存成功");
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductForm form) {
        int row = productService.update(productId, form);

        if (row <= 0) {
            throw new ServiceException("商品更新失败");
        }

        return R.ok("商品更新成功");
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('PRODUCT:DELETE')")
    public R<Void> deleteProduct(@PathVariable Integer productId) {
        int row = productService.delete(productId);

        if (row <= 0) {
            throw new ServiceException("商品删除失败");
        }

        return R.ok("商品删除成功");
    }

    @PostMapping("/sku")
    @PreAuthorize("hasAuthority('PRODUCT:ADD')")
    public R<Void> addSku(@Valid @RequestBody List<SkuForm> formList) {
        formList.forEach(form -> skuService.insert(form));
        return R.ok("商品规格保存成功");
    }

    @PutMapping("/sku/{skuId}")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> updateSku(@PathVariable Integer skuId, @Valid @RequestBody SkuForm form) {
        skuService.update(skuId, form);
        return R.ok("商品规格更行成功");
    }

    @DeleteMapping("/sku/{skuId}")
    @PreAuthorize("hasAuthority('PRODUCT:DELETE')")
    public R<Void> deleteSku(@PathVariable Integer skuId) {
        skuService.delete(skuId);
        return R.ok("商品规格删除成功");
    }

}
