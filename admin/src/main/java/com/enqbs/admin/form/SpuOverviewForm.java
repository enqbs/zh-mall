package com.enqbs.admin.form;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpuOverviewForm {

    @NotNull(message = "商品ID不能为空")
    private Integer spuId;

    private List<String> pictures;

}
