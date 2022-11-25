package com.weirdo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weirdo.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单权限表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}

