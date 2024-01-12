package com.enqbs.admin.service.product;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.ProductForm;
import com.enqbs.admin.vo.ProductVO;
import com.enqbs.common.util.PageUtil;

public interface SpuService {

    /*
     * 商品列表
     * */
    PageUtil<ProductVO> productVOListPage(Integer categoryId, Integer saleableStatus, Integer newStatus,
                                          Integer recommendStatus, Integer deleteStatus, SortEnum sort,
                                          Integer pageNum, Integer pageSize);

    /*
     * 商品详情
     * */
    ProductVO getProductVO(Integer spuId);

    /*
     * 新增商品
     * */
    int insert(ProductForm form);

    /*
     * 更新商品
     * */
    int update(Integer spuId, ProductForm form);

    /*
     * 删除商品
     * */
    int delete(Integer spuId);

    /*
     * 商品上下架
     * */
    int spuOnAndOffTheShelves(Integer spuId);

}
