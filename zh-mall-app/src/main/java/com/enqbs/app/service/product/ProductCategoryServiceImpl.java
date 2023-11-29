package com.enqbs.app.service.product;

import com.enqbs.app.convert.ProductConvert;
import com.enqbs.app.pojo.vo.ProductCategoryVO;
import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.RedisUtil;
import com.enqbs.generator.dao.ProductCategoryMapper;
import com.enqbs.generator.pojo.ProductCategory;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private ProductService productService;
    @Resource
    private ProductConvert productConvert;
    @Resource
    private ThreadPoolTaskExecutor executor;

    @Override
    public ProductCategoryVO getProductCategoryVO(Integer categoryId) {
        ProductCategory category = productCategoryMapper.selectByPrimaryKey(categoryId);

        if (ObjectUtils.isEmpty(category) || Constants.IS_DELETE.equals(category.getDeleteStatus())) {
            return new ProductCategoryVO();
        }

        ProductCategoryVO categoryVO = productConvert.productCategory2ProductCategoryVO(category);
        List<ProductVO> productVOList = productService.getProductVOList(categoryId);
        categoryVO.setProductList(productVOList);
        return categoryVO;
    }

    @Override
    public List<ProductCategoryVO> getProductCategoryVOList() {
        /*
        * old
        *
        List<ProductCategoryVO> categoryVOList;
        RLock lock = redissonClient.getLock(Constants.PRODUCT_CATEGORY_LIST_LOCK);
        lock.lock();

        try {
            String redisStr = (String) redisUtil.getObject(Constants.PRODUCT_CATEGORY_LIST);

            if (StringUtils.isEmpty(redisStr)) {
                List<ProductCategory> categoryList = productCategoryMapper.selectList();
                categoryVOList = categoryList.stream()
                        .filter(category -> category.getParentId().equals(0))
                        .map(e -> productConvert.productCategory2ProductCategoryVO(e)).collect(Collectors.toList());
                findSubProductCategoryVOList(categoryList, categoryVOList);

                for (ProductCategoryVO categoryVO : categoryVOList) {
                    List<ProductVO> productVOList = productService.getProductVOList(categoryVO.getId());
                    categoryVO.setProductList(productVOList);
                }

                long cacheTimeout;

                if (CollectionUtils.isEmpty(categoryVOList)) {
                    cacheTimeout = 30 * 60 * 1000L;
                    redisUtil.setObject(Constants.PRODUCT_CATEGORY_LIST, Constants.PRESENT, cacheTimeout);  // 避免缓存穿透 redis 存放特殊占位符
                } else {
                    cacheTimeout = 3600 * 24 * 15 * 1000L;
                    String json = GsonUtil.obj2Json(categoryVOList);
                    redisUtil.setObject(Constants.PRODUCT_CATEGORY_LIST, json, cacheTimeout);
                }
            } else {
                if (Constants.PRESENT.equals(redisStr)) {
                    categoryVOList = Collections.emptyList();
                } else {
                    categoryVOList = GsonUtil.json2ArrayList(redisStr, ProductCategoryVO[].class);
                }
            }
        } finally {
            lock.unlock();
        }

        return categoryVOList;
        * */
        String redisStr = redisUtil.getString(Constants.PRODUCT_CATEGORY_LIST);

        if (StringUtils.isEmpty(redisStr)) {
            executor.execute(() -> {
                RLock lock = redissonClient.getLock(Constants.PRODUCT_CATEGORY_LIST_LOCK);
                lock.lock();

                try {
                    String redisStr2 = redisUtil.getString(Constants.PRODUCT_CATEGORY_LIST);
                    /* double check,缓存确实为空再查询数据库 */
                    if (StringUtils.isEmpty(redisStr2)) {
                        List<ProductCategory> categoryList = productCategoryMapper.selectList();
                        List<ProductCategoryVO> categoryVOList = categoryList.stream()
                                .filter(category -> category.getParentId().equals(0))
                                .map(category -> productConvert.productCategory2ProductCategoryVO(category)).collect(Collectors.toList());
                        findSubProductCategoryVOList(categoryList, categoryVOList);
                        categoryVOList.stream()
                                .filter(categoryVO -> !categoryVO.getParentId().equals(0)).collect(Collectors.toList())
                                .forEach(categoryVO -> {
                                    List<ProductVO> productVOList = productService.getProductVOList(categoryVO.getId());
                                    categoryVO.setProductList(productVOList);
                                });
                        long cacheTimeout;
                        /* 避免缓存穿透 redis 存放特殊占位符 */
                        if (CollectionUtils.isEmpty(categoryVOList)) {
                            cacheTimeout = 30 * 60 * 1000L;
                            redisUtil.setString(Constants.PRODUCT_CATEGORY_LIST, Constants.PRESENT, cacheTimeout);
                        } else {
                            cacheTimeout = 3600 * 24 * 15 * 1000L;
                            redisUtil.setString(Constants.PRODUCT_CATEGORY_LIST, GsonUtil.obj2Json(categoryVOList), cacheTimeout);
                        }
                    }
                } finally {
                    lock.unlock();
                }
            });

            return Collections.emptyList();
        }

        if (Constants.PRESENT.equals(redisStr)) {
            return Collections.emptyList();
        } else {
            return GsonUtil.json2ArrayList(redisStr, ProductCategoryVO[].class);
        }
    }

    private void findSubProductCategoryVOList(List<ProductCategory> categoryList, List<ProductCategoryVO> categoryVOList) {
        categoryVOList.forEach(categoryVO -> {
            List<ProductCategoryVO> subCategoryVOList = categoryList.stream()
                    .filter(category -> categoryVO.getId().equals(category.getParentId()))
                    .map(category -> productConvert.productCategory2ProductCategoryVO(category)).collect(Collectors.toList());
            categoryVO.setCategoryList(subCategoryVOList);
            findSubProductCategoryVOList(categoryList, subCategoryVOList);
        });
    }

}
