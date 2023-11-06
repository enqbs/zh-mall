package com.enqbs.admin.service.product;

import com.enqbs.admin.form.ProductCategoryForm;
import com.enqbs.admin.vo.ProductCategoryVO;

public interface ProductCategoryService {

    /*
    * 商品分类信息
    * */
    ProductCategoryVO getProductCategoryVO(Integer categoryId);

    /*
    * 新增商品分类
    * */
    int insertProductCategory(ProductCategoryForm form);

    /*
    * 修改商品分类
    * */
    int updateProductCategory(Integer categoryId, ProductCategoryForm form);

    /*
    * 删除商品分类
    * */
    int deleteProductCategory(Integer categoryId);

}
