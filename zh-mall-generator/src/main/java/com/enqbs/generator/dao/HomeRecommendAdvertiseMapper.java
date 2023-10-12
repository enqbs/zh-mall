package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.HomeRecommendAdvertise;

public interface HomeRecommendAdvertiseMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(HomeRecommendAdvertise record);

    int insertSelective(HomeRecommendAdvertise record);

    HomeRecommendAdvertise selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomeRecommendAdvertise record);

    int updateByPrimaryKey(HomeRecommendAdvertise record);

}
