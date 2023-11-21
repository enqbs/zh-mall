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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Override
    public ProductCategoryVO getProductCategoryVO(Integer categoryId) {
        ProductCategory category = productCategoryMapper.selectByPrimaryKey(categoryId);

        if (ObjectUtils.isEmpty(category) || Constants.IS_DELETE.equals(category.getDeleteStatus())) {
            return new ProductCategoryVO();
        }

        ProductCategoryVO categoryVO = productConvert.productCategory2ProductCategoryVO(category);
        List<ProductVO> productVOList = productService.getProductVOList(categoryVO.getId());
        categoryVO.setProductList(productVOList);
        return categoryVO;
    }

    @Override
    public List<ProductCategoryVO> getProductCategoryVOList() {
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
                    categoryVOList = new ArrayList<>();
                } else {
                    categoryVOList = GsonUtil.json2ArrayList(redisStr, ProductCategoryVO[].class);
                }
            }
        } finally {
            lock.unlock();
        }

        return categoryVOList;
    }

    private void findSubProductCategoryVOList(List<ProductCategory> categoryList, List<ProductCategoryVO> categoryVOList) {
        categoryVOList.forEach(categoryVO -> {
            List<ProductCategoryVO> subCategoryVOList = categoryList.stream()
                    .filter(category -> categoryVO.getId().equals(category.getParentId()))
                    .map(e -> productConvert.productCategory2ProductCategoryVO(e)).collect(Collectors.toList());
            categoryVO.setCategoryList(subCategoryVOList);
            findSubProductCategoryVOList(categoryList, subCategoryVOList);
        });
    }

}
