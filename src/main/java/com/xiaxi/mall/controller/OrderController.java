package com.xiaxi.mall.controller;

import com.xiaxi.mall.common.ApiRestResponse;
import com.xiaxi.mall.filter.UserFilter;
import com.xiaxi.mall.model.request.CreatOrderReq;
import com.xiaxi.mall.model.vo.OrderVo;
import com.xiaxi.mall.service.impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/order")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/create")
    @Operation(summary = "创建订单")
    public ApiRestResponse createOrder(@Valid CreatOrderReq req) {
        String orderNo = orderService.createOrder(req, UserFilter.getCurrentUser().getId());
        return ApiRestResponse.success(orderNo);
    }
    @GetMapping("/detail")
    @Operation(summary = "创建订单")
    public ApiRestResponse listOrder(Long orderNo) {
        OrderVo orderVo = orderService.GetOrderDetails(orderNo);
        return ApiRestResponse.success(orderVo);
    }
    @PostMapping("/cancel")
    @Operation(summary = "取消订单")
    public ApiRestResponse cancelOrder(Long orderNo) {
        orderService.cancelOrder(orderNo);
        return ApiRestResponse.success();
    }
    @PostMapping("/qrcode")
    @Operation(summary = "生成支付二维码")
    public ApiRestResponse qrcode(Long orderNo) {
        String pngAddress = orderService.qrcode(orderNo);
        return ApiRestResponse.success(pngAddress);
    }
    @PostMapping("/pay")
    @Operation(summary = "支付")
    public ApiRestResponse pay(Long orderNo) {
        orderService.pay(orderNo);
        return ApiRestResponse.success();
    }
    @PostMapping("/complete")
    @Operation(summary = "确认收货")
    public ApiRestResponse complete(Long orderNo) {
        orderService.complete(orderNo);
        return ApiRestResponse.success();
    }
}
