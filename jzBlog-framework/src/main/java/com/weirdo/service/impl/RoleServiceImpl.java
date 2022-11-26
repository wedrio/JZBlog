package com.weirdo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.mapper.RoleMapper;
import com.weirdo.domain.entity.Role;
import com.weirdo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> queryRoleByUserId(Long id) {
        return roleMapper.queryRoleByUserId(id);
    }
}

