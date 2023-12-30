package com.enqbs.admin.service.product;

import com.enqbs.admin.form.SpuSpecForm;
import com.enqbs.admin.vo.SpuSpecVO;

public interface SpuSpecService {

    SpuSpecVO getSpecVO(Integer spuId);

    int insert(SpuSpecForm form);

    int update(SpuSpecForm form);

    int delete(Integer spuId);

}
