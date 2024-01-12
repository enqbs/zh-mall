package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class CartVO implements Serializable {

    private List<CartProductVO> productList = Collections.emptyList();

    private Boolean selectedAll = false;

    private Integer totalQuantity = 0;

    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Override
    public String toString() {
        return "CartVO{" +
                "productList=" + productList +
                ", selectedAll=" + selectedAll +
                ", totalQuantity=" + totalQuantity +
                ", totalPrice=" + totalPrice +
                '}';
    }

}
