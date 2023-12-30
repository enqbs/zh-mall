package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class SysUserRole implements Serializable {

    private Integer userId;

    private Integer roleId;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "SysUserRole{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }

}
