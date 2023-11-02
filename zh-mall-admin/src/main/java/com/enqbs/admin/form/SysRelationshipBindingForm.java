package com.enqbs.admin.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class SysRelationshipBindingForm {

    @NotNull(message = "绑定ID不能为空")
    private Integer bindId;

    @NotEmpty(message = "被绑定ID不能为空")
    private Set<Integer> toIdSet;

    public Integer getBindId() {
        return bindId;
    }

    public void setBindId(Integer bindId) {
        this.bindId = bindId;
    }

    public Set<Integer> getToIdSet() {
        return toIdSet;
    }

    public void setToIdSet(Set<Integer> toIdSet) {
        this.toIdSet = toIdSet;
    }

}
