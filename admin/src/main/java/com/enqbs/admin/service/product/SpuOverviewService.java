package com.enqbs.admin.service.product;

import com.enqbs.admin.form.ProductOverviewForm;
import com.enqbs.admin.vo.SpuOverviewVO;

public interface SpuOverviewService {

    SpuOverviewVO getSpuOverviewVO(Integer spuId);

    int insert(ProductOverviewForm form);

    int update(ProductOverviewForm form);

    int delete(Integer spuId);

}
