package com.enqbs.app.service.product;

import com.enqbs.app.pojo.dto.SkuStockDTO;
import com.enqbs.app.enums.OrderStatusEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.SkuStockMapper;
import com.enqbs.generator.pojo.SkuStock;
import com.enqbs.generator.pojo.SkuStockLock;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SkuStockServiceImpl implements SkuStockService {

    @Resource
    private SkuStockMapper skuStockMapper;

    @Resource
    private SkuStockLockService skuStockLockService;

    @Override
    public List<SkuStock> getStockList(Set<Integer> skuIdSet) {
        return CollectionUtils.isEmpty(skuIdSet) ? Collections.emptyList() : skuStockMapper.selectListBySkuIdSet(skuIdSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockStock(Long orderNo, List<SkuStockDTO> stockDTOList) {
        List<SkuStock> stockList = stockDTOList.stream().map(this::stockDTO2Stock).toList();
        List<SkuStockLock> stockLockList = stockDTOList.stream().map(s -> stockDTO2StockLock(orderNo, s)).toList();
        skuStockLockService.batchInsert(orderNo, stockLockList);
        batchLockSkuStock(orderNo, stockList);
        log.info("订单号:'{}',库存锁定成功.", orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unLockStock(Long orderNo, OrderStatusEnum orderStatusEnum) {
        List<SkuStockLock> stockLockList = skuStockLockService.getStockLockList(orderNo);
        Set<Integer> skuIdSet = stockLockList.stream().map(SkuStockLock::getSkuId).collect(Collectors.toSet());
        Map<Integer, SkuStockLock> stockLockMap = stockLockList.stream().collect(Collectors.toMap(SkuStockLock::getSkuId, v -> v));
        List<SkuStock> stockList = skuStockMapper.selectListBySkuIdSet(skuIdSet);
        stockList.forEach(s -> {
                    SkuStockLock stockLock = stockLockMap.get(s.getSkuId());
                    s.setActualStock(stockLock.getCount());
                    s.setLockStock(stockLock.getCount());
                    s.setStock(stockLock.getCount());
                }
        );
        skuStockLockService.delete(orderNo);
        batchUnLockStock(orderNo, stockList, orderStatusEnum);
        log.info("订单号:'{}',库存解锁成功.", orderNo);
    }

    private void batchLockSkuStock(Long orderNo, List<SkuStock> stockList) {
        int row = skuStockMapper.batchUpdateByStockListLockStock(stockList);

        if (row < stockList.size()) {
            throw new ServiceException("订单号:" + orderNo + ",库存信息更新失败");
        }
    }

    private void batchUnLockStock(Long orderNo, List<SkuStock> stockList, OrderStatusEnum orderStatusEnum) {
        int row = OrderStatusEnum.PAY_SUCCESS.equals(orderStatusEnum) ?
                skuStockMapper.batchUpdateByStockListUnLockStockDelete(stockList) :
                skuStockMapper.batchUpdateByStockListUnLockStockRollback(stockList);

        if (row < stockList.size()) {
            throw new ServiceException("订单号:" + orderNo + ",库存信息更新失败");
        }
    }

    private SkuStock stockDTO2Stock(SkuStockDTO stockDTO) {
        SkuStock stock = new SkuStock();
        stock.setId(stockDTO.getSkuStockId());
        stock.setLockStock(stockDTO.getLockStock());
        stock.setStock(stockDTO.getStock());
        return stock;
    }

    private SkuStockLock stockDTO2StockLock(Long orderNo, SkuStockDTO stockDTO) {
        SkuStockLock stockLock = new SkuStockLock();
        stockLock.setOrderNo(orderNo);
        stockLock.setSkuId(stockDTO.getSkuId());
        stockLock.setSpuId(stockDTO.getSpuId());
        stockLock.setCount(stockDTO.getQuantity());
        return stockLock;
    }

}
