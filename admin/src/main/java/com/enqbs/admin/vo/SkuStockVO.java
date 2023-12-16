package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class SkuStockVO implements Serializable {

    private Integer id;

    private Integer skuId;

    private Integer actualStock;

    private Integer lockStock;

    private Integer stock;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return "SkuStockVO{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", actualStock=" + actualStock +
                ", lockStock=" + lockStock +
                ", stock=" + stock +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
