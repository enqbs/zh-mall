package com.enqbs.admin.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class SysRelationshipBindingForm {

    @NotNull(message = "绑定ID不能为空")
    private Integer bindId;

    @NotEmpty(message = "被绑定ID不能为空")
    private Set<Integer> toIdSet;

}
