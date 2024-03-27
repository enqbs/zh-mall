package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.vo.SkuStockVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.generator.dao.SkuStockMapper;
import com.enqbs.generator.pojo.SkuStock;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkuStockServiceImpl implements SkuStockService {

    @Resource
    private SkuStockMapper skuStockMapper;

    @Resource
    private ProductConvert productConvert;

    @Override
    public List<SkuStockVO> getSkuStockVOList(Set<Integer> skuIdSet) {
        List<SkuStock> skuStockList = CollectionUtils.isEmpty(skuIdSet) ? Collections.emptyList() : skuStockMapper.selectListBySkuIdSet(skuIdSet);

        if (CollectionUtils.isEmpty(skuStockList)) {
            return Collections.emptyList();
        }

        return skuStockList.stream().map(s -> productConvert.skuStock2SkuStockVO(s)).collect(Collectors.toList());
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
        SkuStock skuStock = new SkuStock();
        skuStock.setSkuId(skuId);
        skuStock.setActualStock(count);
        skuStock.setStock(count);
        return skuStockMapper.updateByPrimaryKeySelective(skuStock);
    }

    @Override
    public int delete(Integer skuId) {
        SkuStock skuStock = skuStockMapper.selectBySkuId(skuId);
        skuStock.setDeleteStatus(Constants.IS_DELETE);
        return skuStockMapper.updateByPrimaryKeySelective(skuStock);
    }

}
