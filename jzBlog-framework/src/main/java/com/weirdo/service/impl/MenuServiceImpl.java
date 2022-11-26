package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.constants.SystemConstants;
import com.weirdo.domain.vo.MenuVo;
import com.weirdo.mapper.MenuMapper;
import com.weirdo.domain.entity.Menu;
import com.weirdo.service.MenuService;
import com.weirdo.utils.BeanCopyUtils;
import com.weirdo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * 获取子menu //TODO 需要考虑到查询的子menu是当前用户具有权限能访问的菜单
     * @return
     */
    public List<MenuVo> getAllChildren(MenuVo menu){
        //判断下面是否有孩子节点
        if (!hasChildren(menu)){
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
     * 判断是否有孩子节点
     * @param menu
     * @return
     */
    private boolean hasChildren(MenuVo menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
        queryWrapper.in(Menu::getMenuType,SystemConstants.MENU,SystemConstants.BUTTON);
        queryWrapper.eq(Menu::getParentId,menu.getId());
        return count(queryWrapper)>0;
    }

}

