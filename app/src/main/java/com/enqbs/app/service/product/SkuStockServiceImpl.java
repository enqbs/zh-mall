package com.enqbs.app.service.product;

import com.enqbs.app.pojo.dto.SkuStockDTO;
import com.enqbs.app.enums.OrderStatusEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.SkuStockMapper;
import com.enqbs.generator.pojo.SkuStock;
import com.enqbs.generator.pojo.SkuStockLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    public List<SkuStock> getSkuStockList(Set<Integer> skuIdSet) {
        return skuStockMapper.selectListBySkuIdSet(skuIdSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockSkuStock(Long orderNo, List<SkuStockDTO> skuStockDTOList) {
        List<SkuStock> skuStockList = skuStockDTOList.stream().map(this::skuStockDTO2SkuStock).collect(Collectors.toList());
        List<SkuStockLock> skuStockLockList = skuStockDTOList.stream().map(s -> skuStockDTO2SkuStockLock(orderNo, s)).collect(Collectors.toList());
        skuStockLockService.batchInsert(orderNo, skuStockLockList);
        batchLockSkuStock(orderNo, skuStockList);
        log.info("订单号:'{}',库存锁定成功.", orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unLockSkuStock(Long orderNo, OrderStatusEnum orderStatusEnum) {
        List<SkuStockLock> stockLockList = skuStockLockService.getSkuStockLockList(orderNo);
        Set<Integer> skuIdSet = stockLockList.stream().map(SkuStockLock::getSkuId).collect(Collectors.toSet());
        Map<Integer, SkuStockLock> skuStockLockMap = stockLockList.stream().collect(Collectors.toMap(SkuStockLock::getSkuId, v -> v));
        List<SkuStock> skuStockList = skuStockMapper.selectListBySkuIdSet(skuIdSet);
        skuStockList.forEach(skuStock -> {
                    SkuStockLock skuStockLock = skuStockLockMap.get(skuStock.getSkuId());
                    skuStock.setActualStock(skuStockLock.getCount());
                    skuStock.setLockStock(skuStockLock.getCount());
                    skuStock.setStock(skuStockLock.getCount());
                }
        );
        skuStockLockService.delete(orderNo);
        batchUnLockSkuStock(orderNo, skuStockList, orderStatusEnum);
        log.info("订单号:'{}',库存解锁成功.", orderNo);
    }

    private void batchLockSkuStock(Long orderNo, List<SkuStock> skuStockList) {
        int row = skuStockMapper.batchUpdateBySkuStockListLockStock(skuStockList);

        if (row < skuStockList.size()) {
            throw new ServiceException("订单号:" + orderNo + ",库存信息更新失败");
        }
    }

    private void batchUnLockSkuStock(Long orderNo, List<SkuStock> skuStockList, OrderStatusEnum orderStatusEnum) {
        int row = OrderStatusEnum.PAY_SUCCESS.equals(orderStatusEnum) ?
                skuStockMapper.batchUpdateBySkuStockListUnLockStockDelete(skuStockList) :
                skuStockMapper.batchUpdateBySkuStockListUnLockStockRollback(skuStockList);

        if (row < skuStockList.size()) {
            throw new ServiceException("订单号:" + orderNo + ",库存信息更新失败");
        }
    }

    private SkuStock skuStockDTO2SkuStock(SkuStockDTO skuStockDTO) {
        SkuStock skuStock = new SkuStock();
        skuStock.setId(skuStockDTO.getSkuStockId());
        skuStock.setLockStock(skuStockDTO.getLockStock());
        skuStock.setStock(skuStockDTO.getStock());
        return skuStock;
    }

    private SkuStockLock skuStockDTO2SkuStockLock(Long orderNo, SkuStockDTO skuStockDTO) {
        SkuStockLock skuStockLock = new SkuStockLock();
        skuStockLock.setOrderNo(orderNo);
        skuStockLock.setSkuId(skuStockDTO.getSkuId());
        skuStockLock.setSpuId(skuStockDTO.getSpuId());
        skuStockLock.setCount(skuStockDTO.getQuantity());
        return skuStockLock;
    }

}
