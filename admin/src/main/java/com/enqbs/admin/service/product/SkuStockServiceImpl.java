package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.vo.SkuStockVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.generator.dao.SkuStockMapper;
import com.enqbs.generator.pojo.SkuStock;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SkuStockServiceImpl implements SkuStockService {

    @Resource
    private SkuStockMapper skuStockMapper;

    @Resource
    private ProductConvert productConvert;

    @Override
    public List<SkuStockVO> getStockVOList(Set<Integer> skuIdSet) {
        List<SkuStock> stockList = skuStockMapper.selectListBySkuIdSet(skuIdSet);
        return stockList.stream().map(s -> productConvert.stock2StockVO(s)).toList();
    }

    @Override
    public int insert(Integer skuId, Integer count) {
        SkuStock stock = new SkuStock();
        stock.setSkuId(skuId);
        stock.setActualStock(count);
        stock.setStock(count);
        return skuStockMapper.insertSelective(stock);
    }

    @Override
    public int update(Integer skuId, Integer count) {
        SkuStock stock = new SkuStock();
        stock.setSkuId(skuId);
        stock.setActualStock(count);
        stock.setStock(count);
        return skuStockMapper.updateByPrimaryKeySelective(stock);
    }

    @Override
    public int delete(Integer skuId) {
        SkuStock stock = skuStockMapper.selectBySkuId(skuId);
        stock.setDeleteStatus(Constants.IS_DELETE);
        return skuStockMapper.updateByPrimaryKeySelective(stock);
    }

}
