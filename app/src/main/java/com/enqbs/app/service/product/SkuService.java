package com.enqbs.app.service.product;

import com.enqbs.app.pojo.vo.SkuVO;

import java.util.List;
import java.util.Set;

public interface SkuService {

    List<SkuVO> getSkuVOList(Integer spuId);

    List<SkuVO> getSkuVOList(Set<Integer> skuIdSet, Set<Integer> spuIdSet);

}
