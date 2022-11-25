package com.weirdo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weirdo.domain.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和角色关联表(SysUserRole)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}

