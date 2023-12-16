package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.HomeSlide;

public interface HomeSlideMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(HomeSlide record);

    int insertSelective(HomeSlide record);

    HomeSlide selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomeSlide record);

    int updateByPrimaryKey(HomeSlide record);

}
