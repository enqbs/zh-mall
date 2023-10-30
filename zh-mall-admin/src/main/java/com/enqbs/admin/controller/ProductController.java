package com.enqbs.admin.controller;

import com.enqbs.admin.service.product.ProductService;
import com.enqbs.admin.vo.ProductVO;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize <= 0) {
            pageSize = 10;
        }

        PageUtil<ProductVO> pageProductVOList = productService.getProductVOList(categoryId, saleableStatus, newStatus, recommendStatus, deleteStatus, pageNum, pageSize);
        return R.ok(pageProductVOList);
    }

    @GetMapping("/product/{productId}")
    public R<ProductVO> productDetail(@PathVariable Integer productId) {
        ProductVO productVO = productService.getProductVO(productId);
        return R.ok(productVO);
    }

}
