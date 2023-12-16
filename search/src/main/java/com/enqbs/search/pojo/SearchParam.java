package com.enqbs.search.pojo;

import co.elastic.clients.elasticsearch._types.SortOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchParam {

    private String index;               // 索引

    private String searchField;         // 搜索字段

    private String searchText;          // 搜索文本

    private String sortField;           // 排序字段

    private SortOrder sortOrder;        // 排序

    private Integer pageNum;            // 分页

    private Integer pageSize;           // 分页数

}
