package com.enqbs.app.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ProductCommentForm {

    @NotNull(message = "商品ID不能为空")
    private Integer spuId;

    @NotNull(message = "订单号不能为空")
    private Long orderNo;

    private String skuTitle;

    @NotBlank(message = "评论内容不能为空")
    private String content;

    private List<String> pictures;

    private Integer star;

}
