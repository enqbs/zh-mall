package com.enqbs.admin.service.product;

import com.enqbs.admin.form.ProductCategoryAttributeForm;
import com.enqbs.admin.vo.ProductCategoryAttributeVO;
import com.enqbs.common.util.PageUtil;

public interface ProductCategoryAttributeService {

    PageUtil<ProductCategoryAttributeVO> attributeVOPage(Integer categoryId, Integer deleteStatus, Integer pageNum, Integer pageSize);

    ProductCategoryAttributeVO getAttributeVO(Integer attributeId);

    int insert(ProductCategoryAttributeForm form);

    int update(Integer attributeId, ProductCategoryAttributeForm form);

    int delete(Integer attributeId);

}
