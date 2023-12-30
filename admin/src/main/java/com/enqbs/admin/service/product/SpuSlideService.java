package com.enqbs.admin.service.product;

import com.enqbs.admin.form.SpuSlideForm;
import com.enqbs.admin.vo.SpuSlideVO;

import java.util.List;

public interface SpuSlideService {

    List<String> getSlideList(Integer spuId);

    SpuSlideVO getSlideVO(Integer spuId);

    int insert(SpuSlideForm form);

    int update(SpuSlideForm form);

    int delete(Integer spuId);

}
