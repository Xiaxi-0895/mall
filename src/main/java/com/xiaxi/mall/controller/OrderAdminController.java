package com.xiaxi.mall.controller;

import com.github.pagehelper.PageInfo;
import com.xiaxi.mall.common.ApiRestResponse;
import com.xiaxi.mall.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/order")
public class OrderAdminController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    @Operation(summary = "订单列表")
    public ApiRestResponse listOrder(int pageNo, int pageSize) {
        PageInfo info = orderService.listOrderForAdmin(pageNo,pageSize);
        return ApiRestResponse.success(info);
    }

    @PostMapping("/delivery")
    @Operation(summary = "发货")
    public ApiRestResponse deliveryOrder(Long orderNo) {
        orderService.delivery(orderNo);
        return ApiRestResponse.success();
    }
}
