package com.enqbs.app.service;

import com.enqbs.app.form.OrderForm;
import com.enqbs.app.pojo.vo.OrderConfirmVO;
import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.pojo.Order;
import com.enqbs.generator.pojo.PayInfo;

public interface OrderService {

    /*
    * 订单确认页
    * */
    OrderConfirmVO getOrderConfirmVO();

    /*
    * 保存订单信息订单
    * */
    Long insertOrder(OrderForm form);

    /*
    * 订单详情
    * */
    OrderVO getOrderVO(Long orderNo);

    /*
    * 顶顶那列表
    * */
    PageUtil<OrderVO> getOrderVOList(Integer status, SortEnum sortEnum, Integer pageNum, Integer pageSize);

    /*
    * 签收订单
    * */
    int sign4Order(Long orderNo);

    /*
    * 取消订单
    * */
    void cancelOrder(Long orderNo);

    /*
    * 处理过期订单
    * */
    void handleTimeoutOrder(Order order);

    /*
    * 处理支付成功订单
    * */
    void handlePaySuccessOrder(PayInfo payInfo);

}
