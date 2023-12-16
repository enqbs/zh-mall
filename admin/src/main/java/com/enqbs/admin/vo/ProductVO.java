package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductVO implements Serializable {

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

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    private List<String> slide;

    private List<SkuVO> skuList;

    @Override
    public String toString() {
        return "ProductVO{" +
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
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", slide=" + slide +
                ", skuList=" + skuList +
                '}';
    }

}
