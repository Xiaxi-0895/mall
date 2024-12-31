package com.xiaxi.mall.service.impl;

import com.xiaxi.mall.error.MallException;
import com.xiaxi.mall.model.dao.UserMapper;
import com.xiaxi.mall.model.pojo.User;
import com.xiaxi.mall.model.request.UserRegister;
import com.xiaxi.mall.service.UserService;
import com.xiaxi.mall.utils.Md5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Map;
import static com.xiaxi.mall.error.MallExceptionEnum.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Value("${salt}")
    int salt;
    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }

    @Override
    public void register(UserRegister user) {
        if (userMapper.selectByUsername(user.getUsername()) != null) {throw new MallException(DUPLICATE_USERNAME);}
        else{
            user.setPassword(Md5Utils.getMd5(user.getPassword(),salt));
            User newUser = new User();
            BeanUtils.copyProperties(user,newUser);
            int count = userMapper.insertSelective(newUser);
            if (count != 1) {throw new MallException(REGISTRATION_FAILED);}
        }
    }

    @Override
    public User loginSelect(Map<String, Object> map) {
        map.put("password", Md5Utils.getMd5(map.get("password").toString(),salt));
        User user = userMapper.loginSelect(map);
        if (user == null) {
            throw new MallException(WRONG_PASSWORD);
        }else{
            user.setPassword(null);
            return user;
        }
    }

    @Override
    public void update(User user) {
        int count = userMapper.updateByPrimaryKeySelective(user);
        if (count!=1) throw new MallException(UPDATE_FAILED);
    }

    @Override
    public User adminLogin(Map<String, Object> map) {
        map.put("password", Md5Utils.getMd5(map.get("password").toString(),salt));
        User user = userMapper.loginSelect(map);
        if (user == null) {
            throw new MallException(WRONG_PASSWORD);
        }else if(user.getRole()==2){
            user.setPassword(null);
            return user;
        }else {
            throw new MallException(NOT_ADMINISTRATOR);
        }
    }


}
