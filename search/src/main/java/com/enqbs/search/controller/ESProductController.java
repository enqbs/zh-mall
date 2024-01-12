package com.enqbs.search.controller;

import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import com.enqbs.search.pojo.ESProduct;
import com.enqbs.search.service.ESProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
public class ESProductController {

    @Resource
    private ESProductService esProductService;

    @GetMapping("/search")
    public R<PageUtil<ESProduct>> productSearch(@RequestParam String searchText,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        PageUtil<ESProduct> productListPage = esProductService.search(searchText, pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 20 : pageSize);
        return R.ok(productListPage);
    }

}
