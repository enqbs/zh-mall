package com.enqbs.app.service.product;

import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.SkuStockLockMapper;
import com.enqbs.generator.pojo.SkuStockLock;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuStockLockServiceImpl implements SkuStockLockService {

    @Resource
    private SkuStockLockMapper skuStockLockMapper;

    @Override
    public List<SkuStockLock> getStockLockList(Long orderNo) {
        return skuStockLockMapper.selectListByOrderNo(orderNo);
    }

    @Override
    public void batchInsert(Long orderNo, List<SkuStockLock> stockLockList) {
        int row = skuStockLockMapper.batchInsertByStockLockList(stockLockList);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",库存锁定信息保存失败");
        }
    }

    @Override
    public void delete(Long orderNo) {
        int row = skuStockLockMapper.deleteByOrderNo(orderNo);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",库存锁定信息更新失败");
        }
    }

}
