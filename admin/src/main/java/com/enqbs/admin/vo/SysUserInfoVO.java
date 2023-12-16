package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SysUserInfoVO implements Serializable {

    private Integer id;

    private String username;

    private String nickName;

    private String photo;

    @Override
    public String toString() {
        return "SysUserInfoVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

}
