package com.enqbs.common.util;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class PageUtil<T> {

    private Integer num;

    private Integer size;

    private Long total = 0L;

    private List<T> list = Collections.emptyList();

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
