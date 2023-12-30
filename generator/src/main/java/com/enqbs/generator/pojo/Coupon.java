package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Coupon implements Serializable {

    private Integer id;

    private Integer productId;

    private BigDecimal denomination;

    private BigDecimal condition;

    private Integer quantity;

    private Date startDate;

    private Date endDate;

    private Integer status;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", productId=" + productId +
                ", denomination=" + denomination +
                ", condition=" + condition +
                ", quantity=" + quantity +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
