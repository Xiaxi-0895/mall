package com.xiaxi.mall.service;

import com.xiaxi.mall.model.pojo.User;
import com.xiaxi.mall.model.request.UserRegister;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    User getUser();
    void register(UserRegister user);
    User loginSelect(Map<String, Object> map);
    void update(User user);
    User adminLogin(Map<String, Object> map);
}
