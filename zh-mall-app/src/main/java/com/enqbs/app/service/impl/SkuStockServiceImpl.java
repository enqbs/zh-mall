package com.enqbs.app.service.impl;

import com.enqbs.app.pojo.dto.SkuStockDTO;
import com.enqbs.app.service.SkuStockService;
import com.enqbs.common.enums.OrderStatusEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.SkuStockLockMapper;
import com.enqbs.generator.dao.SkuStockMapper;
import com.enqbs.generator.pojo.SkuStock;
import com.enqbs.generator.pojo.SkuStockLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private SkuStockLockMapper skuStockLockMapper;

    @Override
    public List<SkuStock> getSkuStockList(Set<Integer> skuIdSet) {
        return skuStockMapper.selectListBySkuIdSet(skuIdSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockSkuStock(Long orderNo, List<SkuStockDTO> skuStockDTOList) {
        List<SkuStock> stockList = skuStockDTOList.stream().map(this::skuStockDTOBuildSkuStock).collect(Collectors.toList());
        List<SkuStockLock> skuStockLockList = skuStockDTOList.stream().map(e -> skuStockDTOBuildSkuStockLock(orderNo, e)).collect(Collectors.toList());
        batchUpdateSkuStock(orderNo, stockList);
        batchInsertSkuStockLock(orderNo, skuStockLockList);
        log.info("库存锁定成功,订单号:{}", orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unLockSkuStock(Long orderNo, OrderStatusEnum orderStatusEnum) {
        List<SkuStockLock> stockLockList = skuStockLockMapper.selectListByOrderNo(orderNo);
        Set<Integer> skuIdSet = stockLockList.stream().map(SkuStockLock::getSkuId).collect(Collectors.toSet());
        List<SkuStock> skuStockList = skuStockMapper.selectListBySkuIdSet(skuIdSet);
        List<SkuStock> updateSkuStockList = new ArrayList<>();
        Map<Integer, SkuStockLock> stockLockMap = stockLockList.stream().collect(Collectors.toMap(SkuStockLock::getSkuId, v -> v));

        for (SkuStock skuStock : skuStockList) {
            SkuStockLock stockLock = stockLockMap.get(skuStock.getSkuId());

            if (OrderStatusEnum.PAY_SUCCESS.equals(orderStatusEnum)) {
                skuStock.setActualStock(skuStock.getActualStock() - stockLock.getCount());
                skuStock.setStock(skuStock.getStock() - stockLock.getCount());
            } else {
                skuStock.setStock(skuStock.getStock() + stockLock.getCount());
            }

            if (ObjectUtils.isNotEmpty(skuStock.getLockStock())) {
                skuStock.setLockStock(skuStock.getLockStock() - stockLock.getCount());
            }

            updateSkuStockList.add(skuStock);
        }

        deleteSkuStockLock(orderNo);
        batchUpdateSkuStock(orderNo, updateSkuStockList);
        log.info("库存解锁成功,订单号:{}", orderNo);
    }

    private void batchUpdateSkuStock(Long orderNo, List<SkuStock> stockList) {
        int row = skuStockMapper.batchUpdateBySkuStockList(stockList);

        if (row < stockList.size()) {
            throw new ServiceException("库存信息更新失败,订单号:" + orderNo);
        }

        log.info("库存信息更新成功,订单号:{}", orderNo);
    }

    private void batchInsertSkuStockLock(Long orderNo, List<SkuStockLock> skuStockLockList) {
        int row = skuStockLockMapper.batchInsertBySkuStockLockList(skuStockLockList);

        if (row <= 0) {
            throw new ServiceException("库存锁定信息保存失败,订单号:" + orderNo);
        }

        log.info("库存锁定信息保存成功,订单号:{}", orderNo);
    }

    private void deleteSkuStockLock(Long orderNo) {
        int row = skuStockLockMapper.deleteByOrderNo(orderNo);

        if (row <= 0) {
            throw new ServiceException("库存锁定信息更新失败,订单号:" + orderNo);
        }

        log.info("库存锁定信息更新成功,订单号:{}", orderNo);
    }

    private SkuStock skuStockDTOBuildSkuStock(SkuStockDTO skuStockDTO) {
        SkuStock skuStock = new SkuStock();
        skuStock.setId(skuStockDTO.getSkuStockId());
        skuStock.setSkuId(skuStockDTO.getSkuId());
        skuStock.setActualStock(skuStockDTO.getActualStock());
        skuStock.setLockStock(skuStockDTO.getLockStock());
        skuStock.setStock(skuStockDTO.getStock());
        return skuStock;
    }

    private SkuStockLock skuStockDTOBuildSkuStockLock(Long orderNo, SkuStockDTO skuStockDTO) {
        SkuStockLock skuStockLock = new SkuStockLock();
        skuStockLock.setOrderNo(orderNo);
        skuStockLock.setSkuId(skuStockDTO.getSkuId());
        skuStockLock.setProductId(skuStockDTO.getProductId());
        skuStockLock.setCount(skuStockDTO.getQuantity());
        return skuStockLock;
    }

}
