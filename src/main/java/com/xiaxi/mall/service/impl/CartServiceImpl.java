package com.xiaxi.mall.service.impl;

import com.xiaxi.mall.constant.Constant;
import com.xiaxi.mall.error.MallException;
import com.xiaxi.mall.error.MallExceptionEnum;
import com.xiaxi.mall.model.dao.CartMapper;
import com.xiaxi.mall.model.dao.CartVoMapper;
import com.xiaxi.mall.model.dao.ProductMapper;
import com.xiaxi.mall.model.pojo.Cart;
import com.xiaxi.mall.model.pojo.Product;
import com.xiaxi.mall.model.pojo.User;
import com.xiaxi.mall.model.vo.CartVo;
import com.xiaxi.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CartVoMapper cartVoMapper;

    @Override
    public List<CartVo> getCartList(int userId) {
        return cartVoMapper.selectByUserId(userId);
    }

    @Override
    public List<CartVo> addProduct(int productId, int num, User user) {
        validProduct(productId,num);
        Cart cart = cartMapper.selectByUserIdAndProductId(user.getId(),productId);
        if(ObjectUtils.isEmpty(cart)){
            cart = new Cart();
            cart.setUserId(user.getId());
            cart.setProductId(productId);
            cart.setQuantity(num);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        }else{
            cart.setQuantity(cart.getQuantity()+num);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.getCartList(user.getId());
    }

    private void validProduct(int productId, int num) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (ObjectUtils.isEmpty(product)) throw new MallException(MallExceptionEnum.INEXISTENT_PRODUCT_ID);
        if (product.getStatus()== Constant.ProductStatus.TAKEN_OFF) throw new MallException(MallExceptionEnum.REMOVED_PRODUCT);
    }

    public List<CartVo> updateCart(int productId, int number,int userId) {
        validProduct(productId,number);
        Cart cart = cartMapper.selectByUserIdAndProductId(userId,productId);
        if (ObjectUtils.isEmpty(cart)){
            throw new MallException(MallExceptionEnum.UPDATE_FAILED);
        }else{
            cart.setQuantity(number);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.getCartList(userId);
    }
    @Override
    public List<CartVo> delete(int productId, Integer id) {
        validProduct(productId,id);
        Cart cart = cartMapper.selectByUserIdAndProductId(id,productId);
        if(ObjectUtils.isEmpty(cart)){
            throw new MallException(MallExceptionEnum.INEXISTENT_PRODUCT_ID);
        }else {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.getCartList(id);
    }
    @Override
    public List<CartVo> selected(Integer productId, int status, Integer userId) {
        Cart cart;
        if (productId!=null){
            validProduct(productId,status);
            cart = cartMapper.selectByUserIdAndProductId(userId,productId);
            if(ObjectUtils.isEmpty(cart)){
                throw new MallException(MallExceptionEnum.INEXISTENT_ID);
            }else {
                cart.setSelected(status);
                cartMapper.selectedOrNot(productId,userId,status);
            }
        }else{
            cartMapper.selectedOrNot(null,userId,status);
        }
        return this.getCartList(userId);
    }



}
