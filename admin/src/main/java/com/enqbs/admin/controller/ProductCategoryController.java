package com.enqbs.admin.controller;

import com.enqbs.admin.form.ProductCategoryAttributeForm;
import com.enqbs.admin.form.ProductCategoryAttributeRelationForm;
import com.enqbs.admin.form.ProductCategoryForm;
import com.enqbs.admin.service.product.ProductCategoryAttributeRelationService;
import com.enqbs.admin.service.product.ProductCategoryAttributeService;
import com.enqbs.admin.service.product.ProductCategoryService;
import com.enqbs.admin.vo.ProductCategoryAttributeVO;
import com.enqbs.admin.vo.ProductCategoryVO;
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
public class ProductCategoryController {

    @Resource
    private ProductCategoryService productCategoryService;

    @Resource
    private ProductCategoryAttributeService productCategoryAttributeService;

    @Resource
    private ProductCategoryAttributeRelationService productCategoryAttributeRelationService;

    @GetMapping("/category/list")
    public R<PageUtil<ProductCategoryVO>> categoryPage(@RequestParam(required = false) Integer parentId,
                                                       @RequestParam(required = false) Integer homeStatus,
                                                       @RequestParam(required = false) Integer naviStatus,
                                                       @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<ProductCategoryVO> categoryVOPage = productCategoryService.categoryVOPage(parentId, homeStatus, naviStatus, deleteStatus,
                pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(categoryVOPage);
    }

    @GetMapping("/category/{categoryId}")
    public R<ProductCategoryVO> categoryDetail(@PathVariable Integer categoryId) {
        ProductCategoryVO categoryInfo = productCategoryService.getCategoryVO(categoryId);
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
            throw new ServiceException("商品分类修改失败");
        }

        return R.ok("商品分类修改成功");
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

    @PutMapping("/category/home/{categoryId}")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> categoryOnAndOffHome(@PathVariable Integer categoryId) {
        int row = productCategoryService.categoryOnAndOffHome(categoryId);

        if (row <= 0) {
            throw new ServiceException("修改失败");
        }

        return R.ok("修改成功");
    }

    @PutMapping("/category/navi/{categoryId}")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> categoryOnAndOffNavi(@PathVariable Integer categoryId) {
        int row = productCategoryService.categoryOnAndOffNavi(categoryId);

        if (row <= 0) {
            throw new ServiceException("修改失败");
        }

        return R.ok("修改成功");
    }

    @GetMapping("/category/attribute/list")
    public R<PageUtil<ProductCategoryAttributeVO>> attributePage(@RequestParam(required = false) Integer categoryId,
                                                                 @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                                                 @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<ProductCategoryAttributeVO> attributeVOPage = productCategoryAttributeService.attributeVOPage(categoryId, deleteStatus,
                pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(attributeVOPage);
    }

    @GetMapping("/category/attribute/{attributeId}")
    public R<?> attributeDetail(@PathVariable Integer attributeId) {
        ProductCategoryAttributeVO attributeInfo = productCategoryAttributeService.getAttributeVO(attributeId);
        return R.ok(attributeInfo);
    }

    @PostMapping("/category/attribute")
    @PreAuthorize("hasAuthority('PRODUCT:ADD')")
    public R<Void> addAttribute(@Valid @RequestBody ProductCategoryAttributeForm form) {
        int row = productCategoryAttributeService.insert(form);

        if (row <= 0) {
            throw new ServiceException("保存失败");
        }

        return R.ok("保存成功");
    }

    @PutMapping("/category/attribute/{attributeId}")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> updateAttribute(@PathVariable Integer attributeId, @Valid @RequestBody ProductCategoryAttributeForm form) {
        int row = productCategoryAttributeService.update(attributeId, form);

        if (row <= 0) {
            throw new ServiceException("修改失败");
        }

        return R.ok("修改成功");
    }

    @DeleteMapping("/category/attribute/{attributeId}")
    @PreAuthorize("hasAuthority('PRODUCT:DELETE')")
    public R<Void> deleteAttribute(@PathVariable Integer attributeId) {
        int row = productCategoryAttributeService.delete(attributeId);

        if (row <= 0) {
            throw new ServiceException("删除失败");
        }

        return R.ok("删除成功");
    }

    @PostMapping("/category/attribute/bind")
    @PreAuthorize("hasAuthority('PRODUCT:ADD')")
    public R<Void> attributeRelationBind(@Valid @RequestBody ProductCategoryAttributeRelationForm form) {
        int row = productCategoryAttributeRelationService.batchInsert(form.getCategoryId(), form.getAttributeIdSet());

        if (row <= 0) {
            throw new ServiceException("保存失败");
        }

        return R.ok("保存成功");
    }

    @PutMapping("/category/attribute/bind")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> updateAttributeRelation(@Valid @RequestBody ProductCategoryAttributeRelationForm form) {
        int row = productCategoryAttributeRelationService.batchUpdate(form.getCategoryId(), form.getAttributeIdSet());

        if (row <= 0) {
            throw new ServiceException("修改失败");
        }

        return R.ok("修改成功");
    }

    @DeleteMapping("/category/attribute/bind")
    @PreAuthorize("hasAuthority('PRODUCT:DELETE')")
    public R<Void> deleteAttributeRelation(@Valid @RequestBody ProductCategoryAttributeRelationForm form) {
        int row = productCategoryAttributeRelationService.batchDelete(form.getCategoryId(), form.getAttributeIdSet());

        if (row <= 0) {
            throw new ServiceException("删除失败");
        }

        return R.ok("删除成功");
    }

}
