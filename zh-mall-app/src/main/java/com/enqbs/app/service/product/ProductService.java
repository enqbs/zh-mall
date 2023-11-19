package com.enqbs.app.service.product;

import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.generator.pojo.Sku;

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

    /*
     * 批量获取商品规格列表
     * */
    List<Sku> getSkuList(Set<Integer> skuIdSet);

}
