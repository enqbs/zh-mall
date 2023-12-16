package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class User implements Serializable {

    private Integer id;

    private Long uid;

    private String nickName;

    private String photo;

    private Integer gender;

    private Integer experience;

    private Integer levelId;

    private Integer status;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", gender=" + gender +
                ", experience=" + experience +
                ", levelId=" + levelId +
                ", status=" + status +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
