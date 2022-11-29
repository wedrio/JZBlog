package com.weirdo.controller;

import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddUserDto;
import com.weirdo.domain.dto.UpdateUserDto;
import com.weirdo.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xiaoli
 * @Date: 2022/11/28 --21:47
 * @Description:
 */
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/list")
    @ApiOperation(value = "用户列表")
    public ResponseResult list(Integer pageNum,Integer pageSize,String userName,String phonenumber,String status){
        return userService.selectPageAllUser(pageNum,pageSize,userName,phonenumber,status);
    }
    @PostMapping
    @ApiOperation(value = "新增用户")
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto){
        return userService.adduser(addUserDto);
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户")
    public ResponseResult deleteUser(@PathVariable("id") Long id){
        return userService.deleteUserById(id);
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询用户信息")
    public ResponseResult selectUserById(@PathVariable("id") Long id){
        return userService.selectUserById(id);
    }
    @PutMapping
    @ApiOperation(value = "更新用户信息")
    public ResponseResult updateUser(@RequestBody UpdateUserDto updateUserDto){
        return userService.updateUser(updateUserDto);
    }
}
