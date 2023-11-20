package com.enqbs.admin.service.product;

import com.enqbs.admin.form.ProductCategoryForm;
import com.enqbs.admin.vo.ProductCategoryVO;
import com.enqbs.common.util.PageUtil;

public interface ProductCategoryService {

    /*
     * 商品分类列表
     * */
    PageUtil<ProductCategoryVO> getProductCategoryVOList(Integer parentId, Integer naviStatus,
                                                         Integer deleteStatus, Integer pageNum, Integer pageSize);

    /*
     * 商品分类信息
     * */
    ProductCategoryVO getProductCategoryVO(Integer categoryId);

    /*
     * 新增商品分类
     * */
    int insert(ProductCategoryForm form);

    /*
     * 修改商品分类
     * */
    int update(Integer categoryId, ProductCategoryForm form);

    /*
     * 删除商品分类
     * */
    int delete(Integer categoryId);

}
