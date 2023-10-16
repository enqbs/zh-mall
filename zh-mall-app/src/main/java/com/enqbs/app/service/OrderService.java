package com.enqbs.app.service;

import com.enqbs.app.form.OrderForm;
import com.enqbs.app.pojo.vo.OrderConfirmVO;
import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.pojo.Order;

public interface OrderService {

    /*
    * 订单确认页
    * */
    OrderConfirmVO getOrderConfirmVO();

    /*
    * 保存订单信息订单
    * */
    OrderVO insertOrder(OrderForm form);

    /*
    * 订单详情
    * */
    OrderVO getOrderVO(Long orderNo);

    /*
    * 顶顶那列表
    * */
    PageUtil<OrderVO> getOrderVOList(Integer status, Integer pageNum, Integer pageSize);

    /*
    * 取消订单
    * */
    int cancelOrder(Long orderNo);

    /*
    * 处理过期订单
    * */
    void handleTimeoutOrder(Order order);

}
