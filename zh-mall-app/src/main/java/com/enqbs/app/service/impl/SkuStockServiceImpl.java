package com.enqbs.app.service.impl;

import com.enqbs.app.pojo.dto.SkuStockDTO;
import com.enqbs.app.service.SkuStockService;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.SkuStockLockMapper;
import com.enqbs.generator.dao.SkuStockMapper;
import com.enqbs.generator.pojo.SkuStock;
import com.enqbs.generator.pojo.SkuStockLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    public void lockSkuStock(List<SkuStockDTO> skuStockDTOList) {
        Set<Long> orderNoSet = skuStockDTOList.stream().map(SkuStockDTO::getOrderNo).collect(Collectors.toSet());
        List<SkuStock> stockList = skuStockDTOList.stream().map(this::skuStockDTOBuildSkuStock).collect(Collectors.toList());
        List<SkuStockLock> skuStockLockList = skuStockDTOList.stream().map(this::skuStockDTOBuildSkuStockLock).collect(Collectors.toList());
        batchUpdateSkuStock(stockList);
        batchInsertSkuStockLock(skuStockLockList);
        log.info("库存锁定成功,订单号:{}", orderNoSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unLockSkuStock(Long orderNo) {
        List<SkuStockLock> stockLockList = skuStockLockMapper.selectListByOrderNo(orderNo);

        if (!CollectionUtils.isEmpty(stockLockList)) {
            Set<Integer> skuIdSet = stockLockList.stream().map(SkuStockLock::getSkuId).collect(Collectors.toSet());
            List<SkuStock> skuStockList = skuStockMapper.selectListBySkuIdSet(skuIdSet);

            if (!CollectionUtils.isEmpty(skuStockList)) {
                Map<Integer, SkuStockLock> stockLockMap = stockLockList.stream()
                        .collect(Collectors.toMap(SkuStockLock::getSkuId, stockLock -> stockLock));
                List<SkuStock> updateSkuStockList = new ArrayList<>();

                for (SkuStock skuStock : skuStockList) {
                    SkuStockLock stockLock = stockLockMap.get(skuStock.getSkuId());

                    if (ObjectUtils.isNotEmpty(skuStock.getLockStock())) {
                        skuStock.setLockStock(skuStock.getLockStock() - stockLock.getCount());
                    }
                    skuStock.setStock(skuStock.getStock() + stockLock.getCount());
                    updateSkuStockList.add(skuStock);
                }
                batchUpdateSkuStock(updateSkuStockList);
                deleteSkuStockLock(orderNo);
                log.info("库存解锁成功,订单号:{}", orderNo);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSkuStock(Long orderNo) {
        List<SkuStockLock> stockLockList = skuStockLockMapper.selectListByOrderNo(orderNo);

        if (!CollectionUtils.isEmpty(stockLockList)) {
            Set<Integer> skuIdSet = stockLockList.stream().map(SkuStockLock::getSkuId).collect(Collectors.toSet());
            List<SkuStock> skuStockList = skuStockMapper.selectListBySkuIdSet(skuIdSet);

            if (!CollectionUtils.isEmpty(skuStockList)) {
                Map<Integer, SkuStockLock> stockLockMap = stockLockList.stream()
                        .collect(Collectors.toMap(SkuStockLock::getSkuId, stockLock -> stockLock));
                List<SkuStock> updateSkuStockList = new ArrayList<>();

                for (SkuStock skuStock : skuStockList) {
                    SkuStockLock stockLock = stockLockMap.get(skuStock.getSkuId());
                    skuStock.setActualStock(skuStock.getActualStock() - stockLock.getCount());

                    if (ObjectUtils.isNotEmpty(skuStock.getLockStock())) {
                        skuStock.setLockStock(skuStock.getLockStock() - stockLock.getCount());
                    }
                    skuStock.setStock(skuStock.getStock() - stockLock.getCount());
                    updateSkuStockList.add(skuStock);
                }
                batchUpdateSkuStock(updateSkuStockList);
                deleteSkuStockLock(orderNo);
                log.info("库存删减成功,订单号:{}", orderNo);
            }
        }
    }

    private void batchUpdateSkuStock(List<SkuStock> stockList) {
        int batchUpdateRow = skuStockMapper.batchUpdateBySkuStockList(stockList);

        if (batchUpdateRow <= 0) {
            throw new ServiceException("库存信息更新失败");
        }
        log.info("库存信息更新成功,{}", stockList);
    }

    private void batchInsertSkuStockLock(List<SkuStockLock> skuStockLockList) {
        int batchInsertRow = skuStockLockMapper.batchInsertBySkuStockLockList(skuStockLockList);

        if (batchInsertRow <= 0) {
            throw new ServiceException("库存锁定信息保存失败");
        }
        log.info("库存锁定信息保存成功,{}", skuStockLockList);
    }

    private void batchUpdateSkuStockLock(List<SkuStockLock> skuStockLockList) {
        int batchUpdateRow = skuStockLockMapper.batchUpdateBySkuStockLockList(skuStockLockList);

        if (batchUpdateRow <= 0) {
            throw new ServiceException("库存锁定信息更新失败");
        }
        log.info("库存锁定信息更新成功,{}", skuStockLockList);
    }

    private void deleteSkuStockLock(Long orderNo) {
        int deleteRow = skuStockLockMapper.deleteByOrderNo(orderNo);

        if (deleteRow <= 0) {
            throw new ServiceException("库存锁定信息更新失败");
        }
        log.info("库存锁定信息更新成功");
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

    private SkuStockLock skuStockDTOBuildSkuStockLock(SkuStockDTO skuStockDTO) {
        SkuStockLock skuStockLock = new SkuStockLock();
        skuStockLock.setOrderNo(skuStockDTO.getOrderNo());
        skuStockLock.setSkuId(skuStockDTO.getSkuId());
        skuStockLock.setProductId(skuStockDTO.getProductId());
        skuStockLock.setCount(skuStockDTO.getQuantity());
        return skuStockLock;
    }

}
