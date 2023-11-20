package com.enqbs.admin.service.product;

import com.enqbs.admin.form.SkuForm;
import com.enqbs.admin.vo.SkuVO;

import java.util.List;
import java.util.Set;

public interface SkuService {

    List<SkuVO> getSkuVOList(Set<Integer> productIdSet);

    List<SkuVO> getSkuVOList(Integer productId);

    void insert(SkuForm form);

    void update(Integer skuId, SkuForm form);

    int delete(Integer skuId);

}
