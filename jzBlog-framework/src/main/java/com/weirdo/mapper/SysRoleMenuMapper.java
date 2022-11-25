package com.weirdo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weirdo.domain.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和菜单关联表(SysRoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

}

