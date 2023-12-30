package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserAuths implements Serializable {

    private Integer id;

    private Integer userId;

    private String identityType;

    private String identifier;

    private String credential;

    private Integer status;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "UserAuths{" +
                "id=" + id +
                ", userId=" + userId +
                ", identityType='" + identityType + '\'' +
                ", identifier='" + identifier + '\'' +
                ", credential='" + credential + '\'' +
                ", status=" + status +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
