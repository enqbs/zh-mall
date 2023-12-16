package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductCommentVO {

    private Integer id;

    private Integer spuId;

    private Long orderNo;

    private Integer userId;

    private String nickName;

    private String photo;

    private String skuTitle;

    private String content;

    private List<String> pictures;

    private Integer star;

    private Integer like;

    private Date createTime;

    private List<ProductCommentReplyVO> replyList;

    @Override
    public String toString() {
        return "ProductCommentVO{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", orderNo=" + orderNo +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", skuTitle='" + skuTitle + '\'' +
                ", content='" + content + '\'' +
                ", pictures=" + pictures +
                ", star=" + star +
                ", like=" + like +
                ", createTime=" + createTime +
                ", replyList=" + replyList +
                '}';
    }

}
