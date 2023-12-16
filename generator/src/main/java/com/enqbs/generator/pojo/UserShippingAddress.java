package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserShippingAddress implements Serializable {

    private Integer id;

    private Integer userId;

    private String name;

    private String telNo;

    private String address;

    private String detailAddress;

    private Integer defaultStatus;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "UserShippingAddress{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", defaultStatus=" + defaultStatus +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
