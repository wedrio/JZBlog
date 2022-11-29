package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weirdo.constants.SystemConstants;
import com.weirdo.domain.entity.LoginUser;
import com.weirdo.domain.entity.User;
import com.weirdo.domain.vo.MenuVo;
import com.weirdo.mapper.MenuMapper;
import com.weirdo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,s);
        User user = userMapper.selectOne(queryWrapper);
        //判断是否查到用户 没查到抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //返回用户信息
        if(user.getType().equals(SystemConstants.ADMIN)){
            List<String> list = menuMapper.queryPermsByUserId(user.getId());
            return new LoginUser(user,list);
        }
        return new LoginUser(user,null);
    }
}
