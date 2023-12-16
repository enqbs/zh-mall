package com.enqbs.search.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ESProduct implements Serializable {

    private Integer id;

    private Integer productCategoryId;

    private String title;

    private String subTitle;

    private String picture;

    private BigDecimal lowestPrice;

    private BigDecimal virtualPrice;

    private Integer saleableStatus;

    private Integer newStatus;

    private Integer recommendStatus;

    private Date createTime;

    @Override
    public String toString() {
        return "ESProduct{" +
                "id=" + id +
                ", productCategoryId=" + productCategoryId +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", picture='" + picture + '\'' +
                ", lowestPrice=" + lowestPrice +
                ", virtualPrice=" + virtualPrice +
                ", saleableStatus=" + saleableStatus +
                ", newStatus=" + newStatus +
                ", recommendStatus=" + recommendStatus +
                ", createTime=" + createTime +
                '}';
    }

}
