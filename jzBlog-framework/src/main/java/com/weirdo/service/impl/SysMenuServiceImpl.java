package com.weirdo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.mapper.SysMenuMapper;
import com.weirdo.domain.entity.SysMenu;
import com.weirdo.service.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

}

