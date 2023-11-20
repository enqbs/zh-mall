package com.enqbs.admin.service.product;

import com.enqbs.admin.form.ProductForm;
import com.enqbs.admin.vo.ProductVO;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;

public interface ProductService {

    /*
     * 商品列表
     * */
    PageUtil<ProductVO> getProductVOList(Integer categoryId, Integer saleableStatus, Integer newStatus,
                                         Integer recommendStatus, Integer deleteStatus, SortEnum sortEnum,
                                         Integer pageNum, Integer pageSize);

    /*
     * 商品详情
     * */
    ProductVO getProductVO(Integer productId);

    /*
     * 新增商品
     * */
    int insert(ProductForm form);

    /*
     * 更新商品
     * */
    int update(Integer productId, ProductForm form);

    /*
     * 删除商品
     * */
    int delete(Integer productId);

}
