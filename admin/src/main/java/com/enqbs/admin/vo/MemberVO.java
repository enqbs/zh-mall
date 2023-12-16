package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
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
