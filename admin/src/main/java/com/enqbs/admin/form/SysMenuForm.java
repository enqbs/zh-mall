package com.enqbs.admin.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SysMenuForm {

    private Integer parentId;

    @NotBlank(message = "菜单标题不能为空")
    private String title;

    private String path;

    private String permissionsKey;

    private Integer sort;

}
