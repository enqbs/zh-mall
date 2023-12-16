package com.enqbs.admin.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class ProductCategoryAttributeRelationForm {

    @NotNull(message = "分类ID不能为空")
    private Integer categoryId;

    @NotEmpty(message = "属性ID不能为空")
    private Set<Integer> attributeIdSet;

}
