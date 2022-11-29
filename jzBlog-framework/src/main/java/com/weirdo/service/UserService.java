package com.weirdo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddUserDto;
import com.weirdo.domain.dto.UpdateUserDto;
import com.weirdo.domain.entity.User;

/**
 * @Author: xiaoli
 * @Date: 2022/11/20 --11:00
 * @Description:
 */
public interface UserService extends IService<User> {
    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    /**
     * 查询用户列表
     * @param pageNum
     * @param pageSize
     * @param phonenumber
     * @param status
     * @return
     */
    ResponseResult selectPageAllUser(Integer pageNum, Integer pageSize, String userName,String phonenumber, String status);

    /**
     * 添加用户
     * @param addUserDto
     * @return
     */
    ResponseResult adduser(AddUserDto addUserDto);

    /**
     * 通过id删除用户（逻辑删除）
     * @param id
     * @return
     */
    ResponseResult deleteUserById(Long id);

    /**
     * 通过id查询用户信息
     * @param id
     * @return
     */
    ResponseResult selectUserById(Long id);

    ResponseResult updateUser(UpdateUserDto updateUserDto);
}
