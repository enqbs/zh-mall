package com.enqbs.app.controller;

import com.enqbs.app.service.ProductCategoryService;
import com.enqbs.app.service.ProductService;
import com.enqbs.app.vo.ProductCategoryVO;
import com.enqbs.app.vo.ProductVO;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private ProductCategoryService productCategoryService;

    @GetMapping("/detail/{productId}")
    public R<ProductVO> productDetail(@PathVariable Integer productId) {
        ProductVO productVO = productService.getProductVO(productId);
        return R.ok(productVO);
    }

    @GetMapping("/category/{categoryId}")
    public R<ProductCategoryVO> productCategory(@PathVariable Integer categoryId) {
        ProductCategoryVO categoryVO = productCategoryService.getProductCategoryVO(categoryId);
        return R.ok(categoryVO);
    }

    @GetMapping("/category/list")
    public R<List<ProductCategoryVO>> productCategoryList() {
        List<ProductCategoryVO> categoryVOList = productCategoryService.getProductCategoryVOList();
        return R.ok(categoryVOList);
    }

}
