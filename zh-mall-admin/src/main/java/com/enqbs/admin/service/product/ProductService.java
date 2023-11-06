package com.enqbs.admin.service.product;

import com.enqbs.admin.form.ProductForm;
import com.enqbs.admin.form.SkuForm;
import com.enqbs.admin.vo.ProductVO;
import com.enqbs.common.util.PageUtil;

public interface ProductService {

    /*
     * 商品列表
     * */
    PageUtil<ProductVO> getProductVOList(Integer categoryId, Integer saleableStatus, Integer newStatus,
                                         Integer recommendStatus, Integer deleteStatus, Integer pageNum, Integer pageSize);

    /*
     * 商品详情
     * */
    ProductVO getProductVO(Integer productId);

    /*
     * 新增商品
     * */
    int insertProduct(ProductForm form);

    /*
     * 更新商品
     * */
    int updateProduct(Integer productId, ProductForm form);

    /*
     * 删除商品
     * */
    int deleteProduct(Integer productId);

    /*
     * 新增商品规格
     * */
    void insertSku(SkuForm form);

    /*
     * 更新商品规格
     * */
    void updateSku(Integer skuId, SkuForm form);

    /*
     * 删除商品规格
     * */
    int deleteSku(Integer skuId);

}
