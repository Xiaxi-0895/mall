package com.xiaxi.mall.model.dao;

import com.xiaxi.mall.model.pojo.User;
import com.xiaxi.mall.model.request.UserRegister;

import java.util.Map;


public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User row);

    int insertSelective(User row);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);

    User selectByUsername(String userName);

    User loginSelect(Map<String, Object> map);
}