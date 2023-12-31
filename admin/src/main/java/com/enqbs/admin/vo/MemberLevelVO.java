package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class MemberLevelVO implements Serializable {

    private Integer id;

    private Integer level;

    private String title;

    private Integer experience;

    private BigDecimal discount;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return "MemberLevelVO{" +
                "id=" + id +
                ", level=" + level +
                ", title='" + title + '\'' +
                ", experience=" + experience +
                ", discount=" + discount +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
