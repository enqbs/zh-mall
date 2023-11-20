package com.enqbs.app.service.product;

import com.enqbs.app.pojo.vo.ProductVO;

import java.util.List;
import java.util.Set;

public interface ProductService {

    /*
     * 商品详情
     * */
    ProductVO getProductVO(Integer productId);

    /*
     * 分类 id 获取商品列表
     * */
    List<ProductVO> getProductVOList(Integer categoryId);

    /*
     * 批量获取商品列表
     * */
    List<ProductVO> getProductVOList(Set<Integer> productIdSet);

}
