package com.weirdo.controller;



import com.weirdo.service.SysRoleMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 角色和菜单关联表(SysRoleMenu)表控制层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@RestController
@RequestMapping("sysRoleMenu")
public class SysRoleMenuController  {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleMenuService sysRoleMenuService;


}

