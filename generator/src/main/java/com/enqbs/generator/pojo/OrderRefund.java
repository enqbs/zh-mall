package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OrderRefund implements Serializable {

    private Long id;

    private Long orderNo;

    private Integer userId;

    private String nickName;

    private String photo;

    private String picture;

    private String content;

    private String reply;

    private BigDecimal refundAmount;

    private Integer status;

    private Integer deleteStatus;

    private Integer consumeVersion;

    private Integer sharding;

    private Date createTime;

    private Date refundTime;

    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "OrderRefund{" +
                "id=" + id +
                ", orderNo=" + orderNo +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", picture='" + picture + '\'' +
                ", content='" + content + '\'' +
                ", reply='" + reply + '\'' +
                ", refundAmount=" + refundAmount +
                ", status=" + status +
                ", deleteStatus=" + deleteStatus +
                ", consumeVersion=" + consumeVersion +
                ", sharding=" + sharding +
                ", createTime=" + createTime +
                ", refundTime=" + refundTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
