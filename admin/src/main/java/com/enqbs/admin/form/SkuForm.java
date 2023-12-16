package com.enqbs.admin.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class SkuForm {

    @NotNull(message = "商品ID不能为空")
    private Integer spuId;

    private String picture;

    private String title;

    private List<SkuParam> params;

    @NotBlank(message = "价格不能为空")
    private String price;

    @NotNull(message = "库存数量不能为空")
    private Integer stock;

}

@Getter
@Setter
class SkuParam {

    @NotBlank(message = "规格属性不能为空")
    private String paramKey;

    @NotBlank(message = "规格属性值不能为空")
    private String paramValue;

}
