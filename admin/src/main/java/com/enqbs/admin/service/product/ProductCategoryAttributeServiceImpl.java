package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.form.ProductCategoryAttributeForm;
import com.enqbs.admin.vo.ProductCategoryAttributeVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.ProductCategoryAttributeMapper;
import com.enqbs.generator.pojo.ProductCategoryAttribute;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryAttributeServiceImpl implements ProductCategoryAttributeService {

    @Resource
    private ProductCategoryAttributeMapper productCategoryAttributeMapper;

    @Resource
    private ProductConvert productConvert;

    @Override
    public PageUtil<ProductCategoryAttributeVO> getAttributeVOList(Integer categoryId, Integer deleteStatus,
                                                                   Integer pageNum, Integer pageSize) {
        PageUtil<ProductCategoryAttributeVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        List<ProductCategoryAttribute> attributeList = productCategoryAttributeMapper
                .selectListByParam(categoryId, deleteStatus, pageNum, pageSize);

        if (CollectionUtils.isEmpty(attributeList)) {
            return pageUtil;
        }

        Long total = productCategoryAttributeMapper.countByParam(null, deleteStatus);
        List<ProductCategoryAttributeVO> attributeVOList = attributeList.stream()
                .map(e -> productConvert.attribute2AttributeVO(e)).collect(Collectors.toList());
        pageUtil.setTotal(total);
        pageUtil.setList(attributeVOList);
        return pageUtil;
    }

    @Override
    public ProductCategoryAttributeVO getAttributeVO(Integer attributeId) {
        ProductCategoryAttribute attribute = productCategoryAttributeMapper.selectByPrimaryKey(attributeId);
        return ObjectUtils.isEmpty(attribute) ?
                new ProductCategoryAttributeVO() : productConvert.attribute2AttributeVO(attribute);
    }

    @Override
    public int insert(ProductCategoryAttributeForm form) {
        ProductCategoryAttribute attribute = productConvert.attributeForm2Attribute(form);
        return productCategoryAttributeMapper.insertSelective(attribute);
    }

    @Override
    public int update(Integer attributeId, ProductCategoryAttributeForm form) {
        ProductCategoryAttribute attribute = productConvert.attributeForm2Attribute(form);
        attribute.setId(attributeId);
        return productCategoryAttributeMapper.updateByPrimaryKeySelective(attribute);
    }

    @Override
    public int delete(Integer attributeId) {
        ProductCategoryAttribute attribute = new ProductCategoryAttribute();
        attribute.setId(attributeId);
        attribute.setDeleteStatus(Constants.IS_DELETE);
        return productCategoryAttributeMapper.updateByPrimaryKeySelective(attribute);
    }

}
