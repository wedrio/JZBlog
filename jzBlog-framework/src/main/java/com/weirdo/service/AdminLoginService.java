package com.weirdo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.entity.User;

/**
 * @Author: xiaoli
 * @Date: 2022/11/20 --11:00
 * @Description:
 */
public interface AdminLoginService extends IService<User> {
    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult getInfo();

    ResponseResult getRouters();
}
