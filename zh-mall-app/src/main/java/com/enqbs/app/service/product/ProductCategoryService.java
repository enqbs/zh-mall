package com.enqbs.app.service.product;

import com.enqbs.app.pojo.vo.ProductCategoryVO;

import java.util.List;

public interface ProductCategoryService {

    /*
     * 商品分类
     * */
    ProductCategoryVO getProductCategoryVO(Integer categoryId);

    /*
     * 商品分类列表
     * */
    List<ProductCategoryVO> getProductCategoryVOList();

}
