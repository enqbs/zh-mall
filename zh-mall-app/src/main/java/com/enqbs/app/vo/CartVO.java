package com.enqbs.app.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartVO {

    private List<CartProductVO> productVOList;

    private Boolean selectedAll;

    private Integer totalQuantity;

    private BigDecimal totalPrice;

    public List<CartProductVO> getProductVOList() {
        return productVOList;
    }

    public void setProductVOList(List<CartProductVO> productVOList) {
        this.productVOList = productVOList;
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
                "productVOList=" + productVOList +
                ", selectedAll=" + selectedAll +
                ", totalQuantity=" + totalQuantity +
                ", totalPrice=" + totalPrice +
                '}';
    }

}
