package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.constants.SystemConstants;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.entity.RoleMenu;
import com.weirdo.domain.vo.MenuTreeVo;
import com.weirdo.domain.vo.MenuVo;
import com.weirdo.domain.vo.SelectMenuTreeVo;
import com.weirdo.enums.AppHttpCodeEnum;
import com.weirdo.exception.SystemException;
import com.weirdo.mapper.MenuMapper;
import com.weirdo.domain.entity.Menu;
import com.weirdo.service.MenuService;
import com.weirdo.service.RoleMenuService;
import com.weirdo.utils.BeanCopyUtils;
import com.weirdo.utils.SecurityUtils;
import org.omg.CORBA.TRANSACTION_MODE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> queryPermsByUserId(Long userId) {
        return menuMapper.queryPermsByUserId(userId);
    }

    @Override
    public List<MenuVo> selectAllRouterMenu() {
        //先查根路由
        List<MenuVo> menus = null;
        List<MenuVo> rootMenus = menuMapper.selectAllRootRouterMenu();
        //再查询子路由
        for (MenuVo rootMenu : rootMenus) {
            rootMenu.setChildren(getAllChildren(rootMenu));
        }
        menus = rootMenus;
        return menus;
    }

    @Override
    public List<MenuVo> selectAllRouterMenuByUserId() {
        Long userId = SecurityUtils.getUserId();
        //先通过userId查出根menu
        List<MenuVo> menus = menuMapper.selectAllRouterMenuByUserId(userId);
        for (MenuVo menu : menus) {
            menu.setChildren(getAllChildren(menu));
        }
        return menus;
    }

    @Override
    public ResponseResult selectAllMenu(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status),Menu::getStatus,status);
        queryWrapper.like(StringUtils.hasText(menuName),Menu::getMenuName,menuName);
        List<Menu> list = list(queryWrapper);
        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectMenuById(Long id) {
        Menu menu = getById(id);
        return ResponseResult.okResult(menu);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if (menu.getId().equals(menu.getParentId())){
            throw new SystemException(AppHttpCodeEnum.UPDATE_MENU_ERROR);
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long id) {
        if (hasChildren(id)){
            throw new SystemException(AppHttpCodeEnum.UPDATE_MENU_EXIST_CHILDREN_ERROR);
        }
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectTreeMenu() {
        //先查询出根菜单
        List<MenuTreeVo> menuTreeVos = menuMapper.selectAllRootMenu();

        //然后查询子菜单
        for (MenuTreeVo menuTreeVo : menuTreeVos) {
            menuTreeVo.setChildren(getAllChildren(menuTreeVo));
        }
        return ResponseResult.okResult(menuTreeVos);
    }

    @Override
    public ResponseResult selectTreeMenuByRoleId(Long id) {
        //查出所有菜单的树结构
        ResponseResult result = selectTreeMenu();
        List<MenuTreeVo> menuTreeVos = (List<MenuTreeVo>) result.getData();
        //查出当前角色具备的菜单
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        List<RoleMenu> roleMenus = roleMenuService.list(queryWrapper);
        List<String> menuIds = roleMenus.stream().map(roleMenu -> roleMenu.getMenuId().toString()).collect(Collectors.toList());
        //设置到响应的Vo对象
        SelectMenuTreeVo selectMenuTreeVo = new SelectMenuTreeVo();
        selectMenuTreeVo.setMenus(menuTreeVos);
        selectMenuTreeVo.setCheckedKeys(menuIds);
        return ResponseResult.okResult(selectMenuTreeVo);
    }

    /**
     * 获取子menu //TODO 需要考虑到查询的子menu是当前用户具有权限能访问的菜单
     * @return
     */
    public List<MenuVo> getAllChildren(MenuVo menu){
        //判断下面是否有孩子节点
        if (!hasChildren(menu.getId())){
            return null;
        }
        List<MenuVo> menus = null;
        List<Menu> list = null;
        //判断是不是管理员
        if (SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            queryWrapper.in(Menu::getMenuType,SystemConstants.MENU,SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getParentId,menu.getId());
            list = list(queryWrapper);
        }else {
            //查询menu时需要通过userId验证是否具有权限
            Long userId = SecurityUtils.getUserId();
            list = menuMapper.queryAllByUserId(userId,menu.getId());
        }
        menus = BeanCopyUtils.copyBeanList(list,MenuVo.class);
        for (MenuVo menu1 : menus) {
            menu1.setChildren(getAllChildren(menu1));
        }
        return menus;
    }

    /**
     * 构建树形菜单获取孩子菜单
     * @param menuTreeVo
     * @return
     */
    public List<MenuTreeVo> getAllChildren(MenuTreeVo menuTreeVo){
        //判断下面是否有孩子节点
        if (!hasChildren(menuTreeVo.getId())){
            return null;
        }
        List<MenuTreeVo> menuTreeVos = menuMapper.queryList(menuTreeVo.getId());
        for (MenuTreeVo menu1 : menuTreeVos) {
            menu1.setChildren(getAllChildren(menu1));
        }
        return menuTreeVos;
    }

    /**
     * 判断是否有孩子节点
     * @param id
     * @return
     */
    private boolean hasChildren(Long id) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
        queryWrapper.in(Menu::getMenuType,SystemConstants.MENU,SystemConstants.BUTTON);
        queryWrapper.eq(Menu::getParentId,id);
        return count(queryWrapper)>0;
    }

}

