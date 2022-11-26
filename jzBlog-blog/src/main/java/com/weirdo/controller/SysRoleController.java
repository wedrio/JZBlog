package com.weirdo.controller;


import com.weirdo.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 角色信息表(SysRole)表控制层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@RestController
@RequestMapping("sysRole")
public class SysRoleController  {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;


}

