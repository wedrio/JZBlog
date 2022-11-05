package com.weirdo.controller;




import com.weirdo.service.SysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 菜单权限表(SysMenu)表控制层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@RestController
@RequestMapping("sysMenu")
public class SysMenuController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;


}

