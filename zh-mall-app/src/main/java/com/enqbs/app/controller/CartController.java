package com.enqbs.app.controller;

import com.enqbs.app.form.CartForm;
import com.enqbs.app.service.CartService;
import com.enqbs.app.vo.CartVO;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class CartController {

    @Resource
    private CartService cartService;

    @GetMapping("/cart")
    public R<CartVO> cart() {
        CartVO cartVO = cartService.getCartVO();
        return R.ok(cartVO);
    }

    @PostMapping("/cart")
    public R<CartVO> addCart(@Valid @RequestBody CartForm form) {
        CartVO cartVO = cartService.add(form);
        return R.ok(cartVO);
    }

    @PutMapping("/cart")
    public R<CartVO> updateCart(@Valid @RequestBody CartForm form) {
        CartVO cartVO = cartService.update(form);
        return R.ok(cartVO);
    }

    @DeleteMapping("/cart/{skuId}")
    public R<CartVO> deleteCart(@PathVariable Integer skuId) {
        CartVO cartVO = cartService.delete(skuId);
        return R.ok(cartVO);
    }

    @PutMapping("/cart/selected")
    public R<CartVO> updateCartSelectedAll() {
        CartVO cartVO = cartService.updateSelectedAll();
        return R.ok(cartVO);
    }

    @PutMapping("/cart/not-selected")
    public R<CartVO> updateCartNotSelectedAll() {
        CartVO cartVO = cartService.updateNotSelectedAll();
        return R.ok(cartVO);
    }

    @DeleteMapping("/cart")
    public R<CartVO> clearCart() {
        CartVO cartVO = cartService.clear();
        return R.ok(cartVO);
    }

}
