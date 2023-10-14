package com.enqbs.app.service.impl;

import com.enqbs.app.service.ProductService;
import com.enqbs.app.vo.ProductVO;
import com.enqbs.app.vo.SkuVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.ProductMapper;
import com.enqbs.generator.dao.SkuMapper;
import com.enqbs.generator.pojo.Product;
import com.enqbs.generator.pojo.Sku;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
        Product product = productMapper.selectByPrimaryKey(productId);

        if (ObjectUtils.isEmpty(product) || Constants.IS_DELETE.equals(product.getDeleteStatus())) {
            throw new ServiceException("商品不存在");
        }
        List<Sku> skuList = skuMapper.selectListByProductId(productId);
        List<SkuVO> skuVOList = skuList.stream().map(this::sku2SkuVO).collect(Collectors.toList());
        ProductVO productVO = product2ProductVO(product);
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
        List<ProductVO> productVOList;
        List<Sku> skuList = skuMapper.selectListByProductIdSet(productIdSet);
        List<Product> productList = productMapper.selectListByProductIdSet(productIdSet);
        productVOList = productList.stream().map(this::product2ProductVO).collect(Collectors.toList());

        for (ProductVO productVO : productVOList) {
            List<SkuVO> skuVOList = skuList.stream()
                    .filter(sku -> productVO.getId().equals(sku.getProductId()))
                    .map(this::sku2SkuVO).collect(Collectors.toList());
            productVO.setSkuList(skuVOList);
        }
        return productVOList;
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
