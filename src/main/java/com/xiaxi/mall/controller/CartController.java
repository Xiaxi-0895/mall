package com.xiaxi.mall.controller;

import com.xiaxi.mall.common.ApiRestResponse;
import com.xiaxi.mall.filter.UserFilter;
import com.xiaxi.mall.model.pojo.User;
import com.xiaxi.mall.model.vo.CartVo;
import com.xiaxi.mall.service.impl.CartServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/cart")
public class CartController {
    @Autowired
    private CartServiceImpl cartService;


    @PostMapping("/add")
    @Operation(summary = "添加商品至购物车")
    public ApiRestResponse addProduct(int productId, int number) {
        User user = UserFilter.getCurrentUser();
        List<CartVo> list = cartService.addProduct(productId,number,user);
        return ApiRestResponse.success(list);
    }

    @GetMapping("/list")
    @Operation(summary = "获取购物车列表")
    public ApiRestResponse getCartList() {
        List<CartVo> list = cartService.getCartList(UserFilter.getCurrentUser().getId());
        return ApiRestResponse.success(list);
    }

    @PostMapping("/update")
    @Operation(summary = "更新某一商品数量到购物车")
    public ApiRestResponse updateCart(int productId, int number){
        List<CartVo> list = cartService.updateCart(productId,number,UserFilter.getCurrentUser().getId());
        return ApiRestResponse.success(list);
    }

    @PostMapping("/delete")
    @Operation(summary = "从购物车删除某一商品")
    public ApiRestResponse deleteCart(int productId){
        List<CartVo> list = cartService.delete(productId,UserFilter.getCurrentUser().getId());
        return ApiRestResponse.success(list);
    }

    @PostMapping("/check")
    @Operation(summary = "（不）选中某一商品")
    public ApiRestResponse selectProductInCart(int productId,int status){
        List<CartVo> list = cartService.selected(productId,status,UserFilter.getCurrentUser().getId());
        return ApiRestResponse.success(list);
    }

    @PostMapping("/checkAll")
    @Operation(summary = "购物车商品全选")
    public ApiRestResponse selectAllProductInCart(int status){
        List<CartVo> list = cartService.selected(null,status,UserFilter.getCurrentUser().getId());
        return ApiRestResponse.success(list);
    }
}
