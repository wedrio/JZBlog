package com.weirdo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.domain.entity.RoleMenu;
import com.weirdo.mapper.RoleMenuMapper;
import com.weirdo.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * @Author: xiaoli
 * @Date: 2022/11/28 --19:19
 * @Description:
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
}
