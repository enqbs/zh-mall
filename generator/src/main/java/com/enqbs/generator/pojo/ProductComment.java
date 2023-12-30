package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class ProductComment implements Serializable {

    private Integer id;

    private Integer spuId;

    private Long orderNo;

    private Integer userId;

    private String nickName;

    private String photo;

    private String skuTitle;

    private String content;

    private String pictures;

    private Integer star;

    private Integer like;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ProductComment{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", orderNo=" + orderNo +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", skuTitle='" + skuTitle + '\'' +
                ", content='" + content + '\'' +
                ", pictures='" + pictures + '\'' +
                ", star=" + star +
                ", like=" + like +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
