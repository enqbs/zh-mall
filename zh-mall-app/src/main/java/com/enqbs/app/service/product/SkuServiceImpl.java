package com.enqbs.app.service.product;

import com.enqbs.app.convert.ProductConvert;
import com.enqbs.app.pojo.vo.SkuVO;
import com.enqbs.generator.dao.SkuMapper;
import com.enqbs.generator.pojo.Sku;
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

    @Resource
    private ProductConvert productConvert;

    @Override
    public List<SkuVO> getSkuVOList(Integer productId) {
        List<Sku> skuList = skuMapper.selectListByProductId(productId);
        return skuList.stream().map(e -> productConvert.sku2SkuVO(e)).collect(Collectors.toList());
    }

    @Override
    public List<SkuVO> getSkuVOList(Set<Integer> skuIdSet, Set<Integer> productIdSet) {
        List<Sku> skuList;

        if (CollectionUtils.isEmpty(skuIdSet)) {
            skuList = skuMapper.selectListByProductIdSet(productIdSet);
        } else {
            skuList = skuMapper.selectListByIdSet(skuIdSet);
        }

        return skuList.stream().map(e -> productConvert.sku2SkuVO(e)).collect(Collectors.toList());
    }

}
