package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class ProductCommentReply implements Serializable {

    private Integer id;

    private Integer commentId;

    private Integer userId;

    private String nickName;

    private String photo;

    private Integer toUserId;

    private String toNickName;

    private String content;

    private Integer like;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ProductCommentReply{" +
                "id=" + id +
                ", commentId=" + commentId +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", toUserId=" + toUserId +
                ", toNickName='" + toNickName + '\'' +
                ", content='" + content + '\'' +
                ", like=" + like +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
