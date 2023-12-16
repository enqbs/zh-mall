package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.HomeBanner;

public interface HomeBannerMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(HomeBanner record);

    int insertSelective(HomeBanner record);

    HomeBanner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomeBanner record);

    int updateByPrimaryKey(HomeBanner record);

}
