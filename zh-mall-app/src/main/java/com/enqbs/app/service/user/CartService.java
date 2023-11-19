package com.enqbs.app.service.user;

import com.enqbs.app.form.CartForm;
import com.enqbs.app.pojo.vo.CartProductVO;
import com.enqbs.app.pojo.vo.CartVO;

import java.util.List;

public interface CartService {

    /*
     * 获取购物车商品
     * */
    CartVO getCartVO();

    /*
     * 添加商品到购物车
     * */
    CartVO add(CartForm form);

    /*
     * 修改购物车商品
     * */
    CartVO update(CartForm form);

    /*
     * 删除购物车商品
     * */
    CartVO delete(Integer skuId);

    /*
     * 购物车商品全选
     * */
    CartVO updateSelectedAll();

    /*
     * 购物车商品取消全选
     * */
    CartVO updateNotSelectedAll();

    /*
     * 清空购物车
     * */
    CartVO clear();

    /*
     * 获取购物车中选中的商品
     * */
    List<CartProductVO> getCartProductVOListBySelected();

    /*
     * 删除购物车选中商品
     * */
    void deleteCartProductVOListBySelected();

}
