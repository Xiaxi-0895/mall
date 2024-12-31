package com.xiaxi.mall.service;

import com.xiaxi.mall.model.pojo.Cart;
import com.xiaxi.mall.model.pojo.User;
import com.xiaxi.mall.model.vo.CartVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    List<CartVo> getCartList(int userId);

    public List<CartVo> addProduct(int productId, int num, User user);

    List<CartVo> delete(int productId, Integer id);

    List<CartVo> selected(Integer productId, int status, Integer id);

}
