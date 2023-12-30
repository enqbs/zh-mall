package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PayRefund implements Serializable {

    private Long id;

    private Long payInfoId;

    private Long orderNo;

    private Integer platform;

    private String platformNo;

    private BigDecimal payAmount;

    private BigDecimal refundAmount;

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
        return "PayRefund{" +
                "id=" + id +
                ", payInfoId=" + payInfoId +
                ", orderNo=" + orderNo +
                ", platform=" + platform +
                ", platformNo='" + platformNo + '\'' +
                ", payAmount=" + payAmount +
                ", refundAmount=" + refundAmount +
                ", deleteStatus=" + deleteStatus +
                ", consumeVersion=" + consumeVersion +
                ", sharding=" + sharding +
                ", createTime=" + createTime +
                ", refundTime=" + refundTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
