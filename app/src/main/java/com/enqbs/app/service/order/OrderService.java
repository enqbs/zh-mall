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

    /**
     * 订单确认页
     *
     * @return 订单确认信息
     */
    OrderConfirmVO getOrderConfirmVO();

    /**
     * 切换优惠券重新计算订单金额
     *
     * @param form OrderConfirmForm
     * @return 订单确认信息
     */
    OrderConfirmVO getOrderConfirmVO(OrderConfirmForm form);

    /**
     * 提交订单
     *
     * @param form OrderForm
     * @return 订单号
     */
    Long submit(OrderForm form);

    /**
     * 订单详情
     *
     * @param orderNo 订单号
     * @return 订单详情
     */
    OrderVO getOrderVO(Long orderNo);

    /**
     * 订单列表（分页）
     *
     * @param status 订单状态
     * @param sort 排序
     * @param pageNum pn
     * @param pageSize ps
     * @return 分页信息
     */
    PageUtil<OrderVO> orderVOListPage(Integer status, SortEnum sort, Integer pageNum, Integer pageSize);

    /**
     * 保存订单信息
     *
     * @param orderNo 订单号
     * @param userId 用户 ID
     * @param couponId 优惠券 ID
     * @param orderItemList 订单项列表
     * @param address 订单收货地址快照
     * @param stockList 商品库存
     * @param orderConfirmVO 订单确认信息
     */
    void insert(Long orderNo, Integer userId, Integer couponId, List<OrderItem> orderItemList,
                OrderShippingAddress address, List<SkuStockDTO> stockList, OrderConfirmVO orderConfirmVO);

    /**
     * 签收订单
     *
     * @param orderNo 订单号
     */
    void sign(Long orderNo);

    /**
     * 取消订单
     *
     * @param orderNo 订单号
     */
    void cancel(Long orderNo);

    /**
     * 处理过期订单
     *
     * @param orderNo 订单号
     */
    void handleTimeoutOrder(Long orderNo);

    /**
     * 处理支付成功订单
     *
     * @param orderNo 订单号
     */
    void handlePaySuccessOrder(Long orderNo);

}
