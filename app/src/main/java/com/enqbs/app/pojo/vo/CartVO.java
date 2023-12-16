package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartVO implements Serializable {

    private List<CartProductVO> productList;

    private Boolean selectedAll;

    private Integer totalQuantity;

    private BigDecimal totalPrice;

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
