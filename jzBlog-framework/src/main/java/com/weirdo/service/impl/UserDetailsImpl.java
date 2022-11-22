package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weirdo.domain.entity.LoginUser;
import com.weirdo.domain.entity.User;
import com.weirdo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: xiaoli
 * @Date: 2022/11/20 --10:49
 * @Description:
 */
@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,s);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        return new LoginUser(user);
    }
}
