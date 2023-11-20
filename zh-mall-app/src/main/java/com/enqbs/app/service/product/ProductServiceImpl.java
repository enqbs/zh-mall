package com.enqbs.app.service.product;

import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.app.pojo.vo.SkuVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.generator.dao.ProductMapper;
import com.enqbs.generator.pojo.Product;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private SkuService skuService;

    @Override
    public ProductVO getProductVO(Integer productId) {
        ProductVO productVO = new ProductVO();
        Product product = productMapper.selectByPrimaryKey(productId);

        if (ObjectUtils.isEmpty(product) || Constants.IS_DELETE.equals(product.getDeleteStatus())) {
            return productVO;
        }

        productVO = product2ProductVO(product);
        List<SkuVO> skuVOList = skuService.getSkuVOList(productId);

        if (!CollectionUtils.isEmpty(skuVOList)) {
            productVO.setSkuList(skuVOList);
        }

        return productVO;
    }

    @Override
    public List<ProductVO> getProductVOList(Integer categoryId) {
        List<Product> productList = productMapper.selectListByCategoryId(categoryId);
        return productList.stream().map(this::product2ProductVO).collect(Collectors.toList());
    }

    @Override
    public List<ProductVO> getProductVOList(Set<Integer> productIdSet) {

        List<Product> productList = productMapper.selectListByProductIdSet(productIdSet);

        if (CollectionUtils.isEmpty(productList)) {
            return Collections.emptyList();
        }


        Map<Integer, List<SkuVO>> skuVOListMap = skuService.getSkuVOList(Collections.emptySet(), productIdSet).stream()
                .collect(Collectors.groupingBy(SkuVO::getProductId));
        return productList.stream().map(e -> {
            ProductVO productVO = product2ProductVO(e);
            productVO.setSkuList(skuVOListMap.get(productVO.getId()));
            return productVO;
        }).collect(Collectors.toList());
    }

    private ProductVO product2ProductVO(Product product) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product, productVO);
        return productVO;
    }

}
