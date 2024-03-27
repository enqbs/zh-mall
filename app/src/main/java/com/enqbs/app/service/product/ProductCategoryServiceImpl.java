package com.enqbs.app.service.product;

import com.enqbs.app.convert.ProductConvert;
import com.enqbs.app.pojo.vo.ProductCategoryVO;
import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.RedisUtil;
import com.enqbs.generator.dao.ProductCategoryMapper;
import com.enqbs.generator.pojo.ProductCategory;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private SpuService spuService;
    @Resource
    private ProductConvert productConvert;

    @Override
    public ProductCategoryVO getCategoryVO(Integer categoryId) {
        ProductCategory category = productCategoryMapper.selectByPrimaryKey(categoryId);

        if (ObjectUtils.isEmpty(category) || Constants.IS_DELETE.equals(category.getDeleteStatus())) {
            return null;
        }

        List<ProductVO> productVOList = spuService.getProductVOList(categoryId, null);
        ProductCategoryVO categoryVO = productConvert.category2CategoryVO(category);
        categoryVO.setProductList(productVOList);
        return categoryVO;
    }

    @Override
    public List<ProductCategoryVO> getCategoryVOList(String key, String lockKey,
                                                     Integer homeStatus, Integer naviStatus, Integer limit) {
        String redisStr = redisUtil.getString(key);

        if (StringUtils.isEmpty(redisStr)) {
            RReadWriteLock lock = redissonClient.getReadWriteLock(lockKey);
            RLock readLock = lock.readLock();
            RLock writeLock = lock.writeLock();
            /* 异步执行 MySQL 查询(JDK 21新特性 VirtualThread) */
            Thread.ofVirtual().name("productCategory-handleDBSource").start(() -> {
                        writeLock.lock();

                        try {
                            handleDBSource(key, homeStatus, naviStatus, limit);
                        } finally {
                            writeLock.unlock();
                        }
                    }
            );
            /* 主线程 sleep 100毫秒,确保异步线程先一步执行 */
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            readLock.lock();

            try {
                redisStr = redisUtil.getString(key);
            } finally {
                readLock.unlock();
            }
        }

        return StringUtils.isEmpty(redisStr) || Constants.PRESENT.equals(redisStr) ?
                Collections.emptyList() : GsonUtil.json2ArrayList(redisStr, ProductCategoryVO[].class);
    }

    @Override
    public void removeCacheCategoryVOList(String key) {
        redisUtil.deleteKey(key);
    }

    private void handleDBSource(String key, Integer homeStatus, Integer naviStatus, Integer limit) {
        String redisStr = redisUtil.getString(key);
        /* double check,缓存确实为空再查询数据库 */
        if (StringUtils.isEmpty(redisStr)) {
            /* DBSource */
            List<ProductCategory> categoryList = productCategoryMapper.selectListByParam(null, homeStatus, naviStatus, Constants.IS_NOT_DELETE, null, null);
            List<ProductCategoryVO> categoryVOList = categoryList.stream().filter(c -> c.getParentId().equals(0)).map(c -> productConvert.category2CategoryVO(c)).toList();
            findSubCategoryVOList(categoryList, categoryVOList);
            categoryVOList.forEach(cvo -> cvo.setProductList(spuService.getProductVOList(cvo.getId(), limit)));
            long cacheTimeout;
            /* 避免缓存穿透 redis 存放特殊占位符 */
            if (CollectionUtils.isEmpty(categoryVOList)) {
                cacheTimeout = 30 * 60 * 1000L;
                redisUtil.setString(key, Constants.PRESENT, cacheTimeout);
            } else {
                cacheTimeout = 3600 * 24 * 15 * 1000L;
                redisUtil.setString(key, GsonUtil.obj2Json(categoryVOList), cacheTimeout);
            }
        }
    }

    private void findSubCategoryVOList(List<ProductCategory> categoryList, List<ProductCategoryVO> categoryVOList) {
        categoryVOList.forEach(cvo -> {
                    List<ProductCategoryVO> subCategoryVOList = categoryList.stream()
                            .filter(c -> cvo.getId().equals(c.getParentId()))
                            .map(c -> productConvert.category2CategoryVO(c)).toList();
                    cvo.setCategoryList(subCategoryVOList);
                    findSubCategoryVOList(categoryList, subCategoryVOList);
                }
        );
    }

}
