package com.xiaxi.mall.service;

import com.github.pagehelper.PageInfo;
import com.xiaxi.mall.model.request.CreatOrderReq;
import com.xiaxi.mall.model.vo.OrderVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    String createOrder(CreatOrderReq req, int userId);

    OrderVo GetOrderDetails(Long orderId);

    void cancelOrder(Long orderNo);

    String qrcode(Long orderNo);

    PageInfo listOrderForAdmin(int pageNo, int pageSize);

    void delivery(Long orderNo);

    void complete(Long orderNo);
}
