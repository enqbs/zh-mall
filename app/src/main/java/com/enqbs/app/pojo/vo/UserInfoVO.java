package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class UserInfoVO implements Serializable {

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

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "userId=" + userId +
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
