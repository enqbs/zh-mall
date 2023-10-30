package com.enqbs.admin.vo;

import java.io.Serializable;
import java.util.Date;

public class MemberVO implements Serializable {

    private Integer id;

    private Long uid;

    private String nickName;

    private String photo;

    private Integer gender;

    private Integer experience;

    private Integer levelId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private MemberLevelVO levelInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public MemberLevelVO getLevelInfo() {
        return levelInfo;
    }

    public void setLevelInfo(MemberLevelVO levelInfo) {
        this.levelInfo = levelInfo;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "id=" + id +
                ", uid=" + uid +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", gender=" + gender +
                ", experience=" + experience +
                ", levelId=" + levelId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", levelInfo=" + levelInfo +
                '}';
    }

}
