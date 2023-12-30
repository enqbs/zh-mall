package com.enqbs.admin.service.product;

import com.enqbs.admin.form.SpuOverviewForm;
import com.enqbs.admin.vo.SpuOverviewVO;

public interface SpuOverviewService {

    SpuOverviewVO getOverviewVO(Integer spuId);

    int insert(SpuOverviewForm form);

    int update(SpuOverviewForm form);

    int delete(Integer spuId);

}
