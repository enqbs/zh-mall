package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Sku implements Serializable {

    private Integer id;

    private Integer spuId;

    private String picture;

    private String title;

    private String params;

    private BigDecimal price;

    private Integer saleableStatus;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Sku{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", picture='" + picture + '\'' +
                ", title='" + title + '\'' +
                ", params='" + params + '\'' +
                ", price=" + price +
                ", saleableStatus=" + saleableStatus +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
