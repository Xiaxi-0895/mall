package com.xiaxi.mall.model.dao;

import com.xiaxi.mall.model.pojo.Cart;

import java.util.List;


public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart row);

    int insertSelective(Cart row);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart row);

    int updateByPrimaryKey(Cart row);

    Cart selectByUserIdAndProductId(Integer userId,Integer productId);

    void selectedOrNot(Integer productId ,int userId,int status);
}