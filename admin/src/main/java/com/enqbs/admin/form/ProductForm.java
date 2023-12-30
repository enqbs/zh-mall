package com.enqbs.admin.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {

    private Integer productCategoryId;

    @NotBlank(message = "商品标题不能为空")
    private String title;

    private String subTitle;

    private String picture;

    private String lowestPrice;

    private String virtualPrice;

    private Integer saleableStatus;

    private Integer newStatus;

    private Integer recommendStatus;

}
