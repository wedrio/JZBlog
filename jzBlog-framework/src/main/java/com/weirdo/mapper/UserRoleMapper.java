package com.weirdo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weirdo.domain.entity.RoleMenu;
import com.weirdo.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xiaoli
 * @Date: 2022/11/28 --19:20
 * @Description:
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
