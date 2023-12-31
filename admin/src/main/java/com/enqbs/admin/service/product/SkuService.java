package com.enqbs.admin.service.product;

import com.enqbs.admin.form.SkuForm;
import com.enqbs.admin.vo.SkuVO;

import java.util.List;
import java.util.Set;

public interface SkuService {

    List<SkuVO> getSkuVOList(Set<Integer> spuIdSet);

    List<SkuVO> getSkuVOList(Integer spuId);

    void insert(SkuForm form);

    void update(Integer skuId, SkuForm form);

    void delete(Integer skuId);

}
