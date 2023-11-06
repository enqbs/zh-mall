package com.enqbs.admin.service.product;

import com.enqbs.admin.form.ProductCategoryForm;
import com.enqbs.admin.vo.ProductCategoryVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.generator.dao.ProductCategoryMapper;
import com.enqbs.generator.pojo.ProductCategory;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public ProductCategoryVO getProductCategoryVO(Integer categoryId) {
        ProductCategoryVO productCategoryVO = new ProductCategoryVO();
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(categoryId);

        if (ObjectUtils.isEmpty(productCategory) || Constants.IS_DELETE.equals(productCategory.getDeleteStatus())) {
            return productCategoryVO;
        }

        productCategoryVO = productCategory2ProductCategoryVO(productCategory);
        return productCategoryVO;
    }

    @Override
    public int insertProductCategory(ProductCategoryForm form) {
        ProductCategory productCategory = productCategoryForm2ProductCategory(form);
        return productCategoryMapper.insertSelective(productCategory);
    }

    @Override
    public int updateProductCategory(Integer categoryId, ProductCategoryForm form) {
        ProductCategory productCategory = productCategoryForm2ProductCategory(form);
        productCategory.setId(categoryId);
        return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }

    @Override
    public int deleteProductCategory(Integer categoryId) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(categoryId);
        productCategory.setDeleteStatus(Constants.IS_DELETE);
        return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }

    private ProductCategory productCategoryForm2ProductCategory(ProductCategoryForm form) {
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(form, productCategory);
        return productCategory;
    }

    private ProductCategoryVO productCategory2ProductCategoryVO(ProductCategory productCategory) {
        ProductCategoryVO productCategoryVO = new ProductCategoryVO();
        BeanUtils.copyProperties(productCategory, productCategoryVO);
        return productCategoryVO;
    }

}
