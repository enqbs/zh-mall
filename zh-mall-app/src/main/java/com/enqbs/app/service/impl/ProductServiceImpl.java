package com.enqbs.app.service.impl;

import com.enqbs.app.service.ProductService;
import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.app.pojo.vo.SkuVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.generator.dao.ProductMapper;
import com.enqbs.generator.dao.SkuMapper;
import com.enqbs.generator.pojo.Product;
import com.enqbs.generator.pojo.Sku;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private SkuMapper skuMapper;

    @Override
    public ProductVO getProductVO(Integer productId) {
        ProductVO productVO = new ProductVO();
        Product product = productMapper.selectByPrimaryKey(productId);

        if (ObjectUtils.isEmpty(product) || Constants.IS_DELETE.equals(product.getDeleteStatus())) {
            return productVO;
        }

        List<Sku> skuList = skuMapper.selectListByProductId(productId);
        List<SkuVO> skuVOList = skuList.stream().map(this::sku2SkuVO).collect(Collectors.toList());
        productVO = product2ProductVO(product);
        productVO.setSkuList(skuVOList);
        return productVO;
    }

    @Override
    public List<ProductVO> getProductVOList(Integer categoryId) {
        List<Product> productList = productMapper.selectListByCategoryId(categoryId);
        return productList.stream().map(this::product2ProductVO).collect(Collectors.toList());
    }

    @Override
    public List<ProductVO> getProductVOList(Set<Integer> productIdSet) {
        List<ProductVO> productVOList = new ArrayList<>();
        List<Product> productList = productMapper.selectListByProductIdSet(productIdSet);

        if (CollectionUtils.isEmpty(productList)) {
            return productVOList;
        }

        List<Sku> skuList = skuMapper.selectListByProductIdSet(productIdSet);
        Map<Integer, List<SkuVO>> skuVOListMap = skuList.stream().map(this::sku2SkuVO).collect(Collectors.groupingBy(SkuVO::getProductId));
        productList.stream().map(this::product2ProductVO).collect(Collectors.toList()).forEach(productVO -> {
            productVO.setSkuList(skuVOListMap.get(productVO.getId()));
            productVOList.add(productVO);
        });
        return productVOList;
    }

    @Override
    public List<Sku> getSkuList(Set<Integer> skuIdSet) {
        return skuMapper.selectListByIdSet(skuIdSet);
    }

    private SkuVO sku2SkuVO(Sku sku) {
        SkuVO skuVO = new SkuVO();
        BeanUtils.copyProperties(sku, skuVO);
        return skuVO;
    }

    private ProductVO product2ProductVO(Product product) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product, productVO);
        return productVO;
    }

}
