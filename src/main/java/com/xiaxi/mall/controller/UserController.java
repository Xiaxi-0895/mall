package com.xiaxi.mall.controller;

import com.xiaxi.mall.common.ApiRestResponse;
import com.xiaxi.mall.error.MallExceptionEnum;
import com.xiaxi.mall.model.pojo.User;
import com.xiaxi.mall.model.request.UserRegister;
import com.xiaxi.mall.service.impl.UserServiceImpl;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @Operation(summary = "用户注册", description = "密码进行加盐md5加密后传入数据库")
    @PostMapping("/register")
    public ApiRestResponse register(UserRegister user){
        if(StringUtils.isEmpty(user.getUsername())){
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }else if(StringUtils.isEmpty(user.getPassword())){
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }else if (user.getPassword().length() < 8) {
            return ApiRestResponse.error(MallExceptionEnum.SHORT_PASSWORD);
        }
        userService.register(user);
        return ApiRestResponse.success();
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ApiRestResponse login(String username, String password,HttpSession session){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("username",username);
        map.put("password",password);
        User user = userService.loginSelect(map);
        session.setAttribute("user",user);
        return ApiRestResponse.success(user);
    }

    @Operation(summary = "更新个性签名")
    @PostMapping("/updateSignature")
    public ApiRestResponse updateUserInfo(HttpSession session,String signature){
        User user = (User)session.getAttribute("user");
        if(user == null){return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);}
        User user1= new User();
        user1.setPersonalizedSignature(signature);
        user1.setId(user.getId());
        userService.update(user1);
        return ApiRestResponse.success();
    }

    @Operation(summary = "退出登录")
    @GetMapping("/logout")
    public ApiRestResponse logout(HttpSession session){
        session.removeAttribute("user");
        return ApiRestResponse.success();
    }

    @Operation(summary = "管理员登录")
    @PostMapping("/adminLogin")
    public ApiRestResponse adminLogin(HttpSession session,String username,String password){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("username",username);
        map.put("password",password);
        User user = userService.adminLogin(map);
        session.setAttribute("user",user);
        return ApiRestResponse.success(user);
    }
}
