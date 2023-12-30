package com.enqbs.admin.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SysRelationshipBindingForm {

    @NotNull(message = "绑定ID不能为空")
    private Integer bindId;

    @NotEmpty(message = "被绑定ID不能为空")
    private Set<Integer> toIdSet;

}
