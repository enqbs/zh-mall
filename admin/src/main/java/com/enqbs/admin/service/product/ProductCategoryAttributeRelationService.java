package com.enqbs.admin.service.product;

import java.util.Set;

public interface ProductCategoryAttributeRelationService {

    int batchInsert(Integer categoryId, Set<Integer> attributeIdSet);

    int batchUpdate(Integer categoryId, Set<Integer> attributeIdSet);

    int batchDelete(Integer categoryId, Set<Integer> attributeIdSet);

}
