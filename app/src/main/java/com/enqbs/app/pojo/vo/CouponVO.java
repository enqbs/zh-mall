package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CouponVO implements Serializable {

    private Integer id;

    private Integer productId;

    private BigDecimal denomination;

    private BigDecimal condition;

    private Integer quantity;

    private Date startDate;

    private Date endDate;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return "CouponVO{" +
                "id=" + id +
                ", productId=" + productId +
                ", denomination=" + denomination +
                ", condition=" + condition +
                ", quantity=" + quantity +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
