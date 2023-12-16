package com.enqbs.admin.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryForm {

    private Integer parentId;

    private String name;

    private String icon;

    private Integer sort;

    private Integer homeStatus;

    private Integer naviStatus;

}
