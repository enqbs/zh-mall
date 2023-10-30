package com.enqbs.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MemberLevelVO implements Serializable {

    private Integer id;

    private Integer level;

    private String title;

    private Integer experience;

    private BigDecimal discount;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MemberLevelVO{" +
                "id=" + id +
                ", level=" + level +
                ", title='" + title + '\'' +
                ", experience=" + experience +
                ", discount=" + discount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
