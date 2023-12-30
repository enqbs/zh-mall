package com.enqbs.admin.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductCategoryAttributeRelationForm {

    @NotNull(message = "分类ID不能为空")
    private Integer categoryId;

    @NotEmpty(message = "属性ID不能为空")
    private Set<Integer> attributeIdSet;

}
