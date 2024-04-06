package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.Spu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SpuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Spu record);

    int insertSelective(Spu record);

    Spu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Spu record);

    int updateByPrimaryKey(Spu record);

    List<Spu> selectListByIdSet(@Param("idSet") Set<Integer> idSet);

    List<Spu> selectListByCategoryIdAndLimit(@Param("productCategoryId") Integer productCategoryId, @Param("limit") Integer limit);

    List<Spu> selectListByParam(@Param("productCategoryId") Integer productCategoryId,
                                @Param("saleableStatus") Integer saleableStatus,
                                @Param("newStatus") Integer newStatus,
                                @Param("recommendStatus") Integer recommendStatus,
                                @Param("deleteStatus") Integer deleteStatus,
                                @Param("sort") String sort,
                                @Param("pageNum") Integer pageNum,
                                @Param("pageSize") Integer pageSize);

    Long countByParam(@Param("productCategoryId") Integer productCategoryId,
                      @Param("saleableStatus") Integer saleableStatus,
                      @Param("newStatus") Integer newStatus,
                      @Param("recommendStatus") Integer recommendStatus,
                      @Param("deleteStatus") Integer deleteStatus);

}
