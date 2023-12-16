package com.enqbs.admin.service.product;

import com.enqbs.admin.form.ProductSlideForm;
import com.enqbs.admin.vo.SpuSlideVO;

import java.util.List;

public interface SpuSlideService {

    List<String> getSpuSlideList(Integer spuId);

    SpuSlideVO getSpuSlideVO(Integer spuId);

    int insert(ProductSlideForm form);

    int update(ProductSlideForm form);

    int delete(Integer spuId);

}
