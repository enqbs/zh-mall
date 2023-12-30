package com.enqbs.admin.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

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
