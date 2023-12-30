package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class SkuStockLock implements Serializable {

    private Long orderNo;

    private Integer skuId;

    private Integer spuId;

    private Integer count;

    private Integer status;

    private Integer consumeVersion;

    private Date createTime;

    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "SkuStockLock{" +
                "orderNo=" + orderNo +
                ", skuId=" + skuId +
                ", spuId=" + spuId +
                ", count=" + count +
                ", status=" + status +
                ", consumeVersion=" + consumeVersion +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
