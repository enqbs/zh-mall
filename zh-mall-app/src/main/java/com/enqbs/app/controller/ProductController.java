package com.enqbs.app.controller;

import com.enqbs.app.service.product.ProductCategoryService;
import com.enqbs.app.service.product.ProductService;
import com.enqbs.app.pojo.vo.ProductCategoryVO;
import com.enqbs.app.pojo.vo.ProductVO;
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
        ProductVO productInfo = productService.getProductVO(productId);
        return R.ok(productInfo);
    }

    @GetMapping("/category/{categoryId}")
    public R<ProductCategoryVO> productCategory(@PathVariable Integer categoryId) {
        ProductCategoryVO category = productCategoryService.getProductCategoryVO(categoryId);
        return R.ok(category);
    }

    @GetMapping("/category/list")
    public R<List<ProductCategoryVO>> productCategoryList() {
        List<ProductCategoryVO> categoryList = productCategoryService.getProductCategoryVOList();
        return R.ok(categoryList);
    }

}
