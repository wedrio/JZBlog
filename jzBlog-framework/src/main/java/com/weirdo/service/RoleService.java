package com.weirdo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
public interface RoleService extends IService<Role> {

    List<String> queryRoleByUserId(Long id);
}

