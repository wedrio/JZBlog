package com.weirdo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.ResponseResult;
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

    ResponseResult selectAllMenu(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    /**
     * 通过id查询菜单
     * @param id
     * @return
     */
    ResponseResult selectMenuById(Long id);

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    ResponseResult updateMenu(Menu menu);

    /**
     * 删除菜单，不能删除有子菜单的菜单
     * @param id
     * @return
     */
    ResponseResult deleteMenu(Long id);

    /**
     * 查询树形结构的菜单
     * @return
     */
    ResponseResult selectTreeMenu();

    /**
     * 通过角色id获取菜单树
     * @param id
     * @return
     */
    ResponseResult selectTreeMenuByRoleId(Long id);
}

