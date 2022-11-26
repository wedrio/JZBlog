package com.weirdo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.entity.Menu;
import com.weirdo.domain.vo.MenuVo;

import java.util.List;

/**
 * 菜单权限表(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
public interface MenuService extends IService<Menu> {
    List<String> queryPermsByUserId(Long userId);

    List<MenuVo> selectAllRouterMenu();

    List<MenuVo> selectAllRouterMenuByUserId();
}

