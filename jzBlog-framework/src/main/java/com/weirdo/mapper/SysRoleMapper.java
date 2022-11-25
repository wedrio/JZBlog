package com.weirdo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weirdo.domain.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息表(SysRole)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

}

