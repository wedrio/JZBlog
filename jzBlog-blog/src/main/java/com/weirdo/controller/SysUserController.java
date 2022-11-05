package com.weirdo.controller;




import com.weirdo.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用户表(SysUser)表控制层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController  {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;


}

