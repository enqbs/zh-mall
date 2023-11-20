package com.enqbs.app.service.user;

import com.enqbs.app.form.CartForm;
import com.enqbs.app.pojo.Cart;
import com.enqbs.app.service.product.ProductService;
import com.enqbs.app.pojo.vo.CartProductVO;
import com.enqbs.app.pojo.vo.CartVO;
import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.app.pojo.vo.SkuVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.RedisUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserService userService;

    @Resource
    private ProductService productService;

    @Override
    public CartVO getCartVO() {
        CartVO cartVO = new CartVO();
        List<CartProductVO> cartProductVOList = new ArrayList<>();      // 购物车商品列表
        boolean selectedAll = true;                                     // 购物车是否全选
        int totalQuantity = 0;                                          // 购物车选中商品数量
        BigDecimal totalPrice = BigDecimal.ZERO;                        // 购物车选中商品总价

        UserInfoVO userInfoVO = userService.getUserInfoVO();
        String redisKey = String.format(Constants.USER_CART_REDIS_KEY, userInfoVO.getUserId());
        Map<Object, Object> redisMap = redisUtil.getRedisMap(redisKey);
        /* 获取购物车中的 productId 集合 */
        Set<Integer> productIdSet = new HashSet<>();

        for (Map.Entry<Object, Object> entry : redisMap.entrySet()) {
            Cart cart = (Cart) entry.getValue();
            productIdSet.add(cart.getProductId());
        }

        if (CollectionUtils.isEmpty(productIdSet)) {
            cartVO.setProductList(cartProductVOList);
            cartVO.setSelectedAll(false);
            cartVO.setTotalQuantity(totalQuantity);
            cartVO.setTotalPrice(totalPrice);
            return cartVO;
        }
        /* 批量获取商品 */
        Map<Integer, ProductVO> productVOMap = productService.getProductVOList(productIdSet).stream()
                .collect(Collectors.toMap(ProductVO::getId, v -> v));

        for (Map.Entry<Object, Object> entry : redisMap.entrySet()) {
            Cart cart = (Cart) entry.getValue();
            ProductVO productVO = productVOMap.get(cart.getProductId());
            SkuVO skuVO = productVO.getSkuList().stream().collect(Collectors.toMap(SkuVO::getId, v -> v)).get(cart.getSkuId());
            CartProductVO cartProductVO = buildCartProductVO(cart, productVO, skuVO);
            cartProductVOList.add(cartProductVO);

            if (cart.getSelected()) {
                totalPrice = totalPrice.add(cartProductVO.getTotalPrice());     // 计算购物车选中商品总价
            } else {
                selectedAll = false;
            }

            totalQuantity += cart.getQuantity();
        }

        cartVO.setProductList(cartProductVOList);
        cartVO.setSelectedAll(selectedAll);
        cartVO.setTotalQuantity(totalQuantity);
        cartVO.setTotalPrice(totalPrice);
        return cartVO;
    }

    @Override
    public CartVO add(CartForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        ProductVO productVO = productService.getProductVO(form.getProductId());

        if (ObjectUtils.isEmpty(productVO)) {
            throw new ServiceException("商品不存在");
        }

        SkuVO skuVO = productVO.getSkuList().stream().collect(Collectors.toMap(SkuVO::getId, v -> v)).get(form.getSkuId());

        if (Constants.PRODUCT_NOT_SHELVES.equals(productVO.getSaleableStatus()) || ObjectUtils.isEmpty(skuVO)) {
            throw new ServiceException("商品下架或删除");
        }

        Cart cart;
        String redisKey = String.format(Constants.USER_CART_REDIS_KEY, userInfoVO.getUserId());
        Object redisMapValue = redisUtil.getRedisMapValue(redisKey, form.getSkuId());

        if (ObjectUtils.isEmpty(redisMapValue)) {
            cart = new Cart(form.getProductId(), form.getSkuId(), form.getQuantity(), form.getSelected());
        } else {
            cart = (Cart) redisMapValue;
            cart.setQuantity(cart.getQuantity() + form.getQuantity());
        }

        redisUtil.setHash(redisKey, form.getSkuId(), cart);
        return getCartVO();
    }

    @Override
    public CartVO update(CartForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        String redisKey = String.format(Constants.USER_CART_REDIS_KEY, userInfoVO.getUserId());
        Cart cart = (Cart) redisUtil.getRedisMapValue(redisKey, form.getSkuId());

        if (ObjectUtils.isEmpty(cart)) {
            throw new ServiceException("购物车中不存在该商品");
        }

        if (ObjectUtils.isNotEmpty(form.getSelected())) {
            cart.setSelected(form.getSelected());
        }

        if (ObjectUtils.isNotEmpty(form.getQuantity()) && form.getQuantity() >= 0) {
            cart.setQuantity(form.getQuantity());
        }

        redisUtil.setHash(redisKey, form.getSkuId(), cart);
        return getCartVO();
    }

    @Override
    public CartVO delete(Integer skuId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        String redisKey = String.format(Constants.USER_CART_REDIS_KEY, userInfoVO.getUserId());
        Cart cart = (Cart) redisUtil.getRedisMapValue(redisKey, skuId);

        if (ObjectUtils.isEmpty(cart)) {
            throw new ServiceException("购物车中不存在该商品");
        }

        redisUtil.deleteEntry(redisKey, skuId);
        return getCartVO();
    }

    @Override
    public CartVO updateSelectedAll() {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        String redisKey = String.format(Constants.USER_CART_REDIS_KEY, userInfoVO.getUserId());
        List<Cart> cartList = getCartList();

        if (!CollectionUtils.isEmpty(cartList)) {
            for (Cart cart : cartList) {
                cart.setSelected(true);
                redisUtil.setHash(redisKey, cart.getSkuId(), cart);
            }
        }

        return getCartVO();
    }

    @Override
    public CartVO updateNotSelectedAll() {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        String redisKey = String.format(Constants.USER_CART_REDIS_KEY, userInfoVO.getUserId());
        List<Cart> cartList = getCartList();

        if (!CollectionUtils.isEmpty(cartList)) {
            for (Cart cart : cartList) {
                cart.setSelected(false);
                redisUtil.setHash(redisKey, cart.getSkuId(), cart);
            }
        }

        return getCartVO();
    }

    @Override
    public CartVO clear() {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        String redisKey = String.format(Constants.USER_CART_REDIS_KEY, userInfoVO.getUserId());
        redisUtil.deleteObject(redisKey);
        return getCartVO();
    }

    @Override
    public List<CartProductVO> getCartProductVOListBySelected() {
        return getCartVO().getProductList().stream().filter(CartProductVO::getSelected).collect(Collectors.toList());
    }

    @Override
    public void deleteCartProductVOListBySelected() {
        List<CartProductVO> cartProductVOList = getCartVO().getProductList().stream()
                .filter(CartProductVO::getSelected).collect(Collectors.toList());

        for (CartProductVO cartProductVO : cartProductVOList) {
            delete(cartProductVO.getSkuId());
        }
    }

    private List<Cart> getCartList() {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        String redisKey = String.format(Constants.USER_CART_REDIS_KEY, userInfoVO.getUserId());
        Map<Object, Object> redisMap = redisUtil.getRedisMap(redisKey);
        List<Cart> cartList = new ArrayList<>();

        for (Map.Entry<Object, Object> entry : redisMap.entrySet()) {
            cartList.add((Cart) entry.getValue());
        }

        return cartList;
    }

    private CartProductVO buildCartProductVO(Cart cart, ProductVO productVO, SkuVO skuVO) {
        CartProductVO cartProductVO = new CartProductVO();

        if (ObjectUtils.isNotEmpty(skuVO)) {
            cartProductVO.setSkuId(skuVO.getId());
            cartProductVO.setPicture(skuVO.getPicture());
            cartProductVO.setSkuTitle(skuVO.getTitle());
            cartProductVO.setParam(skuVO.getParam());
            cartProductVO.setPrice(skuVO.getPrice());
            cartProductVO.setTotalPrice(skuVO.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
        }

        cartProductVO.setProductId(productVO.getId());
        cartProductVO.setProductTitle(productVO.getTitle());
        cartProductVO.setProductSubTitle(productVO.getSubTitle());
        cartProductVO.setQuantity(cart.getQuantity());
        cartProductVO.setSaleableStatus(productVO.getSaleableStatus());
        cartProductVO.setSelected(cart.getSelected());
        return cartProductVO;
    }

}