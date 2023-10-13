package com.enqbs.app.service.impl;

import com.enqbs.app.service.ProductCategoryService;
import com.enqbs.app.service.ProductService;
import com.enqbs.app.vo.ProductCategoryVO;
import com.enqbs.app.vo.ProductVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.ProductCategoryMapper;
import com.enqbs.generator.pojo.ProductCategory;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;

    @Resource
    private ProductService productService;

    @Override
    public ProductCategoryVO getProductCategoryVO(Integer categoryId) {
        ProductCategory category = productCategoryMapper.selectByPrimaryKey(categoryId);

        if (ObjectUtils.isEmpty(category) || Constants.IS_DELETE.equals(category.getDeleteStatus())) {
            throw new ServiceException("商品分类不存在");
        }

        ProductCategoryVO categoryVO = productCategory2ProductCategoryVO(category);
        List<ProductVO> productVOList = productService.getProductVOList(categoryVO.getId());
        categoryVO.setProductList(productVOList);
        return categoryVO;
    }

    @Override
    public List<ProductCategoryVO> getProductCategoryVOList() {
        List<ProductCategory> categoryList = productCategoryMapper.selectList();
        List<ProductCategoryVO> categoryVOList = categoryList.stream()
                .filter(category -> category.getParentId().equals(0))
                .map(this::productCategory2ProductCategoryVO).collect(Collectors.toList());
        findSubProductCategoryVOList(categoryList, categoryVOList);

        for (ProductCategoryVO categoryVO : categoryVOList) {
            List<ProductVO> productVOList = productService.getProductVOList(categoryVO.getId());
            categoryVO.setProductList(productVOList);
        }

        return categoryVOList;
    }

    private void findSubProductCategoryVOList(List<ProductCategory> categoryList, List<ProductCategoryVO> categoryVOList) {
        categoryVOList.forEach(categoryVO -> {
            List<ProductCategoryVO> subCategoryVOList = categoryList.stream()
                    .filter(category -> categoryVO.getId().equals(category.getParentId()))
                    .map(this::productCategory2ProductCategoryVO).collect(Collectors.toList());
            categoryVO.setCategoryList(subCategoryVOList);
            findSubProductCategoryVOList(categoryList, subCategoryVOList);
        });
    }

    private ProductCategoryVO productCategory2ProductCategoryVO(ProductCategory category) {
        ProductCategoryVO categoryVO = new ProductCategoryVO();
        BeanUtils.copyProperties(category, categoryVO);
        return categoryVO;
    }

}
