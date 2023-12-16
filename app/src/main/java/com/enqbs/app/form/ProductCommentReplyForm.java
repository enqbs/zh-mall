package com.enqbs.app.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductCommentReplyForm {

    @NotNull(message = "评论ID不能为空")
    private Integer commentId;

    @NotNull(message = "回复用户ID不能为空")
    private Integer toUserId;

    @NotBlank(message = "回复用户昵称不能为空")
    private String toNickName;

    @NotBlank(message = "内容不能为空")
    private String content;

}
