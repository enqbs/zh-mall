package com.enqbs.app.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartForm {

    @NotNull(message = "商品不能为空")
    private Integer spuId;

    @NotNull(message = "商品规格不能为空")
    private Integer skuId;

    @NotNull(message = "数量不能为空")
    private Integer quantity;

    private Boolean selected = true;

}
