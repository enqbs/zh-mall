package com.enqbs.app.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CartVO implements Serializable {

    private List<CartProductVO> productList;

    private Boolean selectedAll;

    private Integer totalQuantity;

    private BigDecimal totalPrice;

    public List<CartProductVO> getProductList() {
        return productList;
    }

    public void setProductList(List<CartProductVO> productList) {
        this.productList = productList;
    }

    public Boolean getSelectedAll() {
        return selectedAll;
    }

    public void setSelectedAll(Boolean selectedAll) {
        this.selectedAll = selectedAll;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

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
