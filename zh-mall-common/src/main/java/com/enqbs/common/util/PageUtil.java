package com.enqbs.common.util;

import java.util.List;

public class PageUtil<T> {

    private Integer num;

    private Integer size;

    private Long total;

    private List<T> list;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageUtil{" +
                "num=" + num +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                '}';
    }

}
