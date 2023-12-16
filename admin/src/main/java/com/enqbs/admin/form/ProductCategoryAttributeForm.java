package com.enqbs.admin.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProductCategoryAttributeForm {

    @NotBlank(message = "属性不能为空")
    private String name;

}
