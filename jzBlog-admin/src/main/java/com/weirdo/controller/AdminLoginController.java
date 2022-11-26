package com.weirdo.controller;

import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.entity.User;
import com.weirdo.service.AdminLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xiaoli
 * @Date: 2022/11/25 --17:16
 * @Description:
 */
@RestController
@Api(tags = "后台登录控制器",description = "控制登录以及后台菜单初始化的接口")
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @PostMapping("/user/login")
    @ApiOperation(value = "后台登录接口")
    public ResponseResult login(@RequestBody User user){
        return adminLoginService.login(user);
    }

    @GetMapping("getInfo")
    @ApiOperation(value = "获取用户的信息",notes = "获取用户的信息包括角色权限属性")
    public ResponseResult getInfo(){
        return adminLoginService.getInfo();
    }

    @GetMapping("/getRouters")
    @ApiOperation(value = "动态获取路由接口",notes = "返回用户所能访问的菜单数据")
    public ResponseResult getRouters(){
        return adminLoginService.getRouters();
    }

    @PostMapping("/user/logout")
    @ApiOperation(value = "退出登录")
    public ResponseResult logout(){
        return adminLoginService.logout();
    }
}
