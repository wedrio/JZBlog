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
        //判断认证是否成功
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或者密码错误");
        }
        //认证成功 使用用户id生成jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String s = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(s);
        //存入redis
        redisCache.setCacheObject("adminLogin:" + s,loginUser);

        //返回前端所需响应格式
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        Long userId = SecurityUtils.getUserId();
        //去redis中删除key
        redisCache.deleteObject("adminLogin:" + userId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成userInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行校验
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        if (!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
        }
        if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
        }
        //排查数据是否在数据库中有重复
        if (userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //对密码进行加密
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        //添加进数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getInfo() {
        List<String> permissions = null;
        List<String> roles = new ArrayList<>();
        //获取登录的用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //如果用户id为1，说明是管理员
        if (loginUser.getUser().getId()==1L){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> list = menuService.list(queryWrapper);
            //是管理员返回全部权限
            permissions = list.stream().map(Menu::getPerms).collect(Collectors.toList());
            //管理员返回角色为admin
            roles.add("admin");
        }else {
            //根据用户id查询权限信息
            permissions = menuService.queryPermsByUserId(loginUser.getUser().getId());
            //TODO 根据用户id查询角色信息
            roles = roleService.queryRoleByUserId(loginUser.getUser().getId());
        }


        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(permissions,roles,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @Override
    public ResponseResult getRouters() {
        List<MenuVo> menus = null;
        //如果是管理员
        if (SecurityUtils.isAdmin()){
            //是 获取所有符合的menu
            menus = menuService.selectAllRouterMenu();
        }else {
            menus = menuService.selectAllRouterMenuByUserId();
        }
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    /**
     * 判断数据库中是否有重复用户名
     * @param userName
     * @return
     */
    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }

    /**
     * 判断数据库中是否有重复别名
     * @param nickName
     * @return
     */
    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0;
    }

    /**
     * 判断数据库中是否有重复邮箱
     * @param email
     * @return
     */
    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper)>0;
    }
}
