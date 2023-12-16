package com.enqbs.app.service.product;

import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.search.pojo.ESProduct;

import java.util.List;
import java.util.Set;

public interface SpuService {

    /*
     * 商品详情
     * */
    ProductVO getProductVO(Integer spuId);

    /*
    * ESProduct
    * */
    ESProduct getESProduct(Integer spuId);

    /*
     * 分类 id 获取商品列表
     * */
    List<ProductVO> getProductVOList(Integer categoryId, Integer limit);

    /*
     * 批量获取商品列表
     * */
    List<ProductVO> getProductVOList(Set<Integer> spuIdSet);

}
