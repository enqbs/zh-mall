package com.enqbs.admin.service.product;

import com.enqbs.admin.form.ProductSpecForm;
import com.enqbs.admin.vo.SpuSpecVO;

public interface SpuSpecService {

    SpuSpecVO getSpuSpecVO(Integer spuId);

    int insert(ProductSpecForm form);

    int update(ProductSpecForm form);

    int delete(Integer spuId);

}
