package com.weirdo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weirdo.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

