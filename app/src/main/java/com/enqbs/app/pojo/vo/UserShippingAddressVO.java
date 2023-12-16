package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserShippingAddressVO implements Serializable {

    private Integer id;

    private Integer userId;

    private String name;

    private String telNo;

    private String address;

    private String detailAddress;

    private Integer defaultStatus;

    private Date createTime;

    @Override
    public String toString() {
        return "UserShippingAddressVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", defaultStatus=" + defaultStatus +
                ", createTime=" + createTime +
                '}';
    }

}
