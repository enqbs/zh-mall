package com.enqbs.app.service.order;

import com.enqbs.app.form.OrderConfirmForm;
import com.enqbs.app.form.OrderForm;
import com.enqbs.app.pojo.dto.SkuStockDTO;
import com.enqbs.app.pojo.vo.OrderConfirmVO;
import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.pojo.OrderItem;
import com.enqbs.generator.pojo.OrderShippingAddress;

import java.util.List;

public interface OrderService {

    /*
     * 订单确认页
     * */
    OrderConfirmVO getOrderConfirmVO();

    /*
     * 切换优惠券重新计算订单金额
     * */
    OrderConfirmVO getOrderConfirmVO(OrderConfirmForm form);

    /*
     * 提交订单
     * */
    Long submit(OrderForm form);

    /*
     * 订单详情
     * */
    OrderVO getOrderVO(Long orderNo);

    /*
     * 顶顶那列表
     * */
    PageUtil<OrderVO> orderVOPage(Integer status, SortEnum sort, Integer pageNum, Integer pageSize);

    /*
     * 保存订单信息
     * */
    void insert(Long orderNo, Integer userId, Integer couponId, List<OrderItem> orderItemList,
                OrderShippingAddress address, List<SkuStockDTO> stockList, OrderConfirmVO orderConfirmVO);

    /*
     * 签收订单
     * */
    void sign(Long orderNo);

    /*
     * 取消订单
     * */
    void cancel(Long orderNo);

    /*
     * 处理过期订单
     * */
    void handleTimeoutOrder(Long orderNo);

    /*
     * 处理支付成功订单
     * */
    void handlePaySuccessOrder(Long orderNo);

}
