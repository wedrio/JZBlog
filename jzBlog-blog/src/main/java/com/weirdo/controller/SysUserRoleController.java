package com.weirdo.controller;



import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用户和角色关联表(SysUserRole)表控制层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@RestController
@RequestMapping("sysUserRole")
public class SysUserRoleController  {
    /**
     * 服务对象
     */
    @Resource
    private SysUserRoleService sysUserRoleService;


}

