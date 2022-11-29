package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oracle.xmlns.internal.webservices.jaxws_databinding.ExistingAnnotationsType;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddUserDto;
import com.weirdo.domain.dto.UpdateUserDto;
import com.weirdo.domain.entity.LoginUser;
import com.weirdo.domain.entity.Role;
import com.weirdo.domain.entity.User;
import com.weirdo.domain.entity.UserRole;
import com.weirdo.domain.vo.*;
import com.weirdo.enums.AppHttpCodeEnum;
import com.weirdo.exception.SystemException;
import com.weirdo.mapper.UserMapper;
import com.weirdo.service.RoleService;
import com.weirdo.service.UserRoleService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: xiaoli
 * @Date: 2022/11/20 --11:02
 * @Description:
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserRoleService userRoleService;

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
        redisCache.setCacheObject("login:" + s,loginUser);

        //返回前端所需响应格式
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        LoginUserVo loginUserVo = new LoginUserVo(jwt, userInfoVo);
        return ResponseResult.okResult(loginUserVo);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        //去redis中删除key
        redisCache.deleteObject("login:" + userid);
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
    public ResponseResult selectPageAllUser(Integer pageNum, Integer pageSize, String userName,String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userName),User::getUserName,userName);
        queryWrapper.eq(StringUtils.hasText(phonenumber),User::getPhonenumber,phonenumber);
        queryWrapper.eq(StringUtils.hasText(status),User::getStatus,status);
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<User> userList = page.getRecords();
        List<AdminUserListVo> adminUserListVos = BeanCopyUtils.copyBeanList(userList, AdminUserListVo.class);
        return ResponseResult.okResult(new PageVo(adminUserListVos,page.getTotal()));
    }

    @Override
    @Transactional
    public ResponseResult adduser(AddUserDto addUserDto) {
        //数据校验
        if (!StringUtils.hasText(addUserDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (userNameExist(addUserDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (emailExist(addUserDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (phoneNumberExist(addUserDto.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONE_NUMBER_EXIST);
        }

        //首先需要添加用户
        //添加用户之前需要对密码进行加密
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        save(user);
        //然后添加用户和角色的关联关系
        List<String> roleIds = addUserDto.getRoleIds();
        for (String roleId : roleIds) {
            userRoleService.save(new UserRole(user.getId(),Long.parseLong(roleId)));
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUserById(Long id) {
        if (SecurityUtils.getUserId() == id){
            throw new SystemException(AppHttpCodeEnum.DELETE_CURRENT_USER_ERROR);
        }
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectUserById(Long id) {
        //通过id查询用户信息并封装成Vo
        User user = getById(id);
        UpdateUserInfoVo updateUserInfoVo = BeanCopyUtils.copyBean(user, UpdateUserInfoVo.class);
        //通过userid查出关联的Role的id集合
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,user.getId());
        List<UserRole> list = userRoleService.list(queryWrapper);
        List<String> roleIds = list.stream().map(role -> role.getRoleId().toString()).collect(Collectors.toList());
        //查出所有角色信息
        List<Role> roles = roleService.list();
        //封装到vo中
        UpdateUserVo updateUserVo = new UpdateUserVo(roleIds, roles, updateUserInfoVo);
        return ResponseResult.okResult(updateUserVo);
    }

    @Override
    @Transactional
    public ResponseResult updateUser(UpdateUserDto updateUserDto) {
        //更新用户信息
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        updateById(user);
        //更新用户和角色的关联信息
        //先删除之前的关联关系
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,user.getId());
        userRoleService.remove(queryWrapper);
        //添加新的关系
        List<String> roleIds = updateUserDto.getRoleIds();
        for (String roleId : roleIds) {
            userRoleService.save(new UserRole(user.getId(), Long.parseLong(roleId)));
        }
        return ResponseResult.okResult();
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
     * 判断数据库中是否有重复手机号
     * @param phonenumber
     * @return
     */
    private boolean phoneNumberExist(String phonenumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phonenumber);
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
