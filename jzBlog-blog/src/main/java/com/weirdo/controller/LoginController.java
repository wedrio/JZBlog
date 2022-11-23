package com.weirdo.controller;

import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.entity.User;
import com.weirdo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xiaoli
 * @Date: 2022/11/20 --10:58
 * @Description:
 */
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return userService.login(user);
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("/logout")
    public ResponseResult logout(){
        return userService.logout();
    }
}
