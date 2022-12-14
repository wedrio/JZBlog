package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.constants.SystemConstants;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.entity.LoginUser;
import com.weirdo.domain.entity.Menu;
import com.weirdo.domain.entity.User;
import com.weirdo.domain.vo.*;
import com.weirdo.enums.AppHttpCodeEnum;
import com.weirdo.exception.SystemException;
import com.weirdo.mapper.UserMapper;
import com.weirdo.service.AdminLoginService;
import com.weirdo.service.MenuService;
import com.weirdo.service.RoleService;
import com.weirdo.service.UserService;
import com.weirdo.utils.BeanCopyUtils;
import com.weirdo.utils.JwtUtil;
import com.weirdo.utils.RedisCache;
import com.weirdo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: xiaoli
 * @Date: 2022/11/20 --11:02
 * @Description:
 */
@Service("adminLoginService")
public class AdminLoginServiceImpl extends ServiceImpl<UserMapper, User> implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;


    @Override
    public ResponseResult login(User user) {
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //????????????????????????
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("???????????????????????????");
        }
        //???????????? ????????????id??????jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String s = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(s);
        //??????redis
        redisCache.setCacheObject("adminLogin:" + s,loginUser);

        //??????????????????????????????
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        Long userId = SecurityUtils.getUserId();
        //???redis?????????key
        redisCache.deleteObject("adminLogin:" + userId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getInfo() {
        List<String> permissions = null;
        List<String> roles = new ArrayList<>();
        //???????????????????????????
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //????????????id???1?????????????????????
        if (loginUser.getUser().getId()==1L){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> list = menuService.list(queryWrapper);
            //??????????????????????????????
            permissions = list.stream().map(Menu::getPerms).collect(Collectors.toList());
            //????????????????????????admin
            roles.add("admin");
        }else {
            //????????????id??????????????????
            permissions = menuService.queryPermsByUserId(loginUser.getUser().getId());
            //TODO ????????????id??????????????????
            roles = roleService.queryRoleByUserId(loginUser.getUser().getId());
        }


        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(permissions,roles,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @Override
    public ResponseResult getRouters() {
        List<MenuVo> menus = null;
        //??????????????????
        if (SecurityUtils.isAdmin()){
            //??? ?????????????????????menu
            menus = menuService.selectAllRouterMenu();
        }else {
            menus = menuService.selectAllRouterMenuByUserId();
        }
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    /**
     * ??????????????????????????????????????????
     * @param userName
     * @return
     */
    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }

    /**
     * ???????????????????????????????????????
     * @param nickName
     * @return
     */
    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0;
    }

    /**
     * ???????????????????????????????????????
     * @param email
     * @return
     */
    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper)>0;
    }
}
