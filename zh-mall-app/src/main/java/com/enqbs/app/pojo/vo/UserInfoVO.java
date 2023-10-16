package com.enqbs.app.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserInfoVO implements Serializable {

    private String userToken;

    private Integer userId;

    private Long uid;

    private String nickName;

    private String photo;

    private Integer gender;

    private Integer experience;

    private Integer level;

    private String levelTitle;

    private Integer levelExperience;

    private BigDecimal discount;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelTitle() {
        return levelTitle;
    }

    public void setLevelTitle(String levelTitle) {
        this.levelTitle = levelTitle;
    }

    public Integer getLevelExperience() {
        return levelExperience;
    }

    public void setLevelExperience(Integer levelExperience) {
        this.levelExperience = levelExperience;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "userToken='" + userToken + '\'' +
                ", userId=" + userId +
                ", uid=" + uid +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", gender=" + gender +
                ", experience=" + experience +
                ", level=" + level +
                ", levelTitle='" + levelTitle + '\'' +
                ", levelExperience=" + levelExperience +
                ", discount=" + discount +
                '}';
    }

}
