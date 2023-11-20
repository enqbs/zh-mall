package com.enqbs.admin.service.product;

import com.enqbs.admin.vo.SkuStockVO;
import com.enqbs.generator.dao.SkuStockMapper;
import com.enqbs.generator.pojo.SkuStock;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkuStockServiceImpl implements SkuStockService {

    @Resource
    private SkuStockMapper skuStockMapper;

    @Override
    public List<SkuStockVO> getSkuStockVOList(Set<Integer> skuIdSet) {
        List<SkuStock> skuStockList = skuStockMapper.selectListBySkuIdSet(skuIdSet);
        return skuStockList.stream().map(this::skuStock2SkuStockVO).collect(Collectors.toList());
    }

    @Override
    public int insert(Integer skuId, Integer count) {
        SkuStock skuStock = new SkuStock();
        skuStock.setSkuId(skuId);
        skuStock.setActualStock(count);
        skuStock.setStock(count);
        return skuStockMapper.insertSelective(skuStock);
    }

    @Override
    public int update(Integer skuId, Integer count) {
        SkuStock skuStock = skuStockMapper.selectBySkuId(skuId);
        skuStock.setActualStock(count);
        skuStock.setStock(count);
        return skuStockMapper.updateByPrimaryKeySelective(skuStock);
    }

    private SkuStockVO skuStock2SkuStockVO(SkuStock skuStock) {
        SkuStockVO skuStockVO = new SkuStockVO();
        BeanUtils.copyProperties(skuStock, skuStockVO);
        return skuStockVO;
    }

}
