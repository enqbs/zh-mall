package com.enqbs.admin.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryAttributeForm {

    @NotBlank(message = "属性不能为空")
    private String name;

}
