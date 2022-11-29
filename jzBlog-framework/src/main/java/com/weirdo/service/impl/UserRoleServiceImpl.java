package com.weirdo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.domain.entity.RoleMenu;
import com.weirdo.domain.entity.UserRole;
import com.weirdo.mapper.RoleMenuMapper;
import com.weirdo.mapper.UserRoleMapper;
import com.weirdo.service.RoleMenuService;
import com.weirdo.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @Author: xiaoli
 * @Date: 2022/11/28 --19:19
 * @Description:
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
