package com.enqbs.admin.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ProductOverviewForm {

    @NotNull(message = "商品ID不能为空")
    private Integer spuId;

    private List<String> pictures;

}
