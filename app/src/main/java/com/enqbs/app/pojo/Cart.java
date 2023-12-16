package com.enqbs.app.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Cart implements Serializable {

    private Integer spuId;

    private Integer skuId;

    private Integer quantity;

    private Boolean selected;

    public Cart(Integer spuId, Integer skuId, Integer quantity, Boolean selected) {
        this.spuId = spuId;
        this.skuId = skuId;
        this.quantity = quantity;
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "spuId=" + spuId +
                ", skuId=" + skuId +
                ", quantity=" + quantity +
                ", selected=" + selected +
                '}';
    }

}
