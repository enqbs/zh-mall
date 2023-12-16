package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class HomeSlide implements Serializable {

    private Integer id;

    private Integer productId;

    private String picture;

    private Integer status;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "HomeSlide{" +
                "id=" + id +
                ", productId=" + productId +
                ", picture='" + picture + '\'' +
                ", status=" + status +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
