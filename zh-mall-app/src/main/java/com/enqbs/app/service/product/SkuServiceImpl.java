package com.enqbs.app.service.product;

import com.enqbs.app.pojo.vo.SkuVO;
import com.enqbs.generator.dao.SkuMapper;
import com.enqbs.generator.pojo.Sku;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkuServiceImpl implements SkuService {

    @Resource
    private SkuMapper skuMapper;

    @Override
    public List<SkuVO> getSkuVOList(Integer productId) {
        List<Sku> skuList = skuMapper.selectListByProductId(productId);
        return skuList.stream().map(this::sku2SkuVO).collect(Collectors.toList());
    }

    @Override
    public List<SkuVO> getSkuVOList(Set<Integer> skuIdSet, Set<Integer> productIdSet) {
        List<Sku> skuList;

        if (CollectionUtils.isEmpty(skuIdSet)) {
            skuList = skuMapper.selectListByProductIdSet(productIdSet);
        } else {
            skuList = skuMapper.selectListByIdSet(skuIdSet);
        }

        return skuList.stream().map(this::sku2SkuVO).collect(Collectors.toList());
    }

    private SkuVO sku2SkuVO(Sku sku) {
        SkuVO skuVO = new SkuVO();
        BeanUtils.copyProperties(sku, skuVO);
        return skuVO;
    }

}
