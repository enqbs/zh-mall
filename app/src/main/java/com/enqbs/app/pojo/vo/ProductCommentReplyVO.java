package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class ProductCommentReplyVO implements Serializable {

    private Integer id;

    private Integer commentId;

    private Integer userId;

    private String nickName;

    private String photo;

    private Integer toUserId;

    private String toNickName;

    private String content;

    private Integer like;

    private Date createTime;

    @Override
    public String toString() {
        return "ProductCommentReplyVO{" +
                "id=" + id +
                ", commentId=" + commentId +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", toUserId=" + toUserId +
                ", toNickName='" + toNickName + '\'' +
                ", content='" + content + '\'' +
                ", like=" + like +
                ", createTime=" + createTime +
                '}';
    }

}
