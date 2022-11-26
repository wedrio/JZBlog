package com.weirdo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weirdo.domain.entity.Menu;
import com.weirdo.domain.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单权限表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> queryPermsByUserId(Long userId);

    List<MenuVo> selectAllRootRouterMenu();

    List<MenuVo> selectAllRouterMenuByUserId(Long userId);

    List<Menu> queryAllByUserId(Long userId,Long id);
}

