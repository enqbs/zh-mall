package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SkuVO implements Serializable {

    private Integer id;

    private Integer spuId;

    private String picture;

    private String title;

    private List<SkuParamVO> params;

    private BigDecimal price;

    private Integer saleableStatus;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    private SkuStockVO skuStock;

    @Override
    public String toString() {
        return "SkuVO{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", picture='" + picture + '\'' +
                ", title='" + title + '\'' +
                ", params=" + params +
                ", price=" + price +
                ", saleableStatus=" + saleableStatus +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", skuStock=" + skuStock +
                '}';
    }

}

