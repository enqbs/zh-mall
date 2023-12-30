package com.enqbs.admin.service.product;

import com.enqbs.generator.dao.ProductCategoryAttributeRelationMapper;
import com.enqbs.generator.pojo.ProductCategoryAttributeRelation;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductCategoryAttributeRelationServiceImpl implements ProductCategoryAttributeRelationService {

    @Resource
    private ProductCategoryAttributeRelationMapper productCategoryAttributeRelationMapper;

    @Override
    public int batchInsert(Integer categoryId, Set<Integer> attributeIdSet) {
        List<ProductCategoryAttributeRelation> attributeRelations = buildAttributeRelationList(categoryId, attributeIdSet);
        return productCategoryAttributeRelationMapper.batchInsertByProductCategoryAttributeRelationList(attributeRelations);
    }

    @Override
    public int batchUpdate(Integer categoryId, Set<Integer> attributeIdSet) {
        productCategoryAttributeRelationMapper.deleteByProductCategoryId(categoryId);
        return batchInsert(categoryId, attributeIdSet);
    }

    @Override
    public int batchDelete(Integer categoryId, Set<Integer> attributeIdSet) {
        List<ProductCategoryAttributeRelation> attributeRelations = buildAttributeRelationList(categoryId, attributeIdSet);
        return productCategoryAttributeRelationMapper.batchDeleteByProductCategoryAttributeRelationList(attributeRelations);
    }

    private ProductCategoryAttributeRelation buildAttributeRelation(Integer productCategoryId, Integer productCategoryAttributeId) {
        ProductCategoryAttributeRelation attributeRelation = new ProductCategoryAttributeRelation();
        attributeRelation.setProductCategoryId(productCategoryId);
        attributeRelation.setProductCategoryAttributeId(productCategoryAttributeId);
        return attributeRelation;
    }

    private List<ProductCategoryAttributeRelation> buildAttributeRelationList(Integer categoryId, Set<Integer> attributeIdSet) {
        return attributeIdSet.stream().map(a -> buildAttributeRelation(categoryId, a)).toList();
    }

}
