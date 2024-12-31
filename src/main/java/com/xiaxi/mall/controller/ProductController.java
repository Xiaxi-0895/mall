package com.xiaxi.mall.controller;

import com.github.pagehelper.PageInfo;
import com.xiaxi.mall.common.ApiRestResponse;
import com.xiaxi.mall.model.pojo.Product;
import com.xiaxi.mall.model.request.ProductListReq;
import com.xiaxi.mall.service.impl.ProductsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class ProductController {
    @Autowired
    private ProductsServiceImpl productsService;

    @GetMapping("/goods/details")
    @Operation(summary = "获取商品详情")
    public ApiRestResponse getGoodsDetails(int id) {
        String details = productsService.getDetails(id);
        return ApiRestResponse.success(details);
    }

    @GetMapping("/goods/list")
    @Operation(summary = "（用户）获取商品列表")
    public ApiRestResponse getGoodsList(ProductListReq req){
        PageInfo info = productsService.listForUser(req);
        return ApiRestResponse.success(info);
    }
}
