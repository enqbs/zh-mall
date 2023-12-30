package com.enqbs.admin.service.product;

import com.enqbs.admin.vo.SkuStockVO;

import java.util.List;
import java.util.Set;

public interface SkuStockService {

    List<SkuStockVO> getStockVOList(Set<Integer> skuIdSet);

    int insert(Integer skuId, Integer count);

    int update(Integer skuId, Integer count);

    int delete(Integer skuId);

}
