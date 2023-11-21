package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.form.ProductCategoryForm;
import com.enqbs.admin.vo.ProductCategoryVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.ProductCategoryMapper;
import com.enqbs.generator.pojo.ProductCategory;
import org.apache.commons.lang3.ObjectUtils;
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
    private ProductConvert productConvert;

    @Override
    public PageUtil<ProductCategoryVO> getProductCategoryVOList(Integer parentId, Integer naviStatus,
                                                                Integer deleteStatus, Integer pageNum, Integer pageSize) {
        PageUtil<ProductCategoryVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<ProductCategory> productCategoryList = productCategoryMapper.selectListByParam(parentId, naviStatus, deleteStatus, pageNum, pageSize);

        if (CollectionUtils.isEmpty(productCategoryList)) {
            pageUtil.setTotal(total);
            pageUtil.setList(Collections.emptyList());
            return pageUtil;
        }

        total = productCategoryMapper.countByParam(parentId, naviStatus, deleteStatus);
        List<ProductCategoryVO> productCategoryVOList = productCategoryList.stream()
                .map(e -> productConvert.productCategory2ProductCategoryVO(e)).collect(Collectors.toList());
        pageUtil.setTotal(total);
        pageUtil.setList(productCategoryVOList);
        return pageUtil;
    }

    @Override
    public ProductCategoryVO getProductCategoryVO(Integer categoryId) {
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(categoryId);

        if (ObjectUtils.isEmpty(productCategory)) {
            return new ProductCategoryVO();
        }

        return productConvert.productCategory2ProductCategoryVO(productCategory);
    }

    @Override
    public int insert(ProductCategoryForm form) {
        ProductCategory productCategory = productConvert.productCategoryForm2ProductCategory(form);
        return productCategoryMapper.insertSelective(productCategory);
    }

    @Override
    public int update(Integer categoryId, ProductCategoryForm form) {
        ProductCategory productCategory = productConvert.productCategoryForm2ProductCategory(form);
        productCategory.setId(categoryId);
        return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }

    @Override
    public int delete(Integer categoryId) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(categoryId);
        productCategory.setDeleteStatus(Constants.IS_DELETE);
        return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }

}
