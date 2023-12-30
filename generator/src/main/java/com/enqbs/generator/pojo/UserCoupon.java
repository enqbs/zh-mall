package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserCoupon implements Serializable {

    private Integer id;

    private Integer couponId;

    private Integer userId;

    private Integer quantity;

    private Integer status;

    private Integer deleteStatus;

    private Integer consumeVersion;

    private Date createTime;

    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "UserCoupon{" +
                "id=" + id +
                ", couponId=" + couponId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", status=" + status +
                ", deleteStatus=" + deleteStatus +
                ", consumeVersion=" + consumeVersion +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
