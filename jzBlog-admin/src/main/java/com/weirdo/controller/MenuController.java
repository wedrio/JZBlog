package com.weirdo.controller;

import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.ChangeStatusDto;
import com.weirdo.domain.entity.Menu;
import com.weirdo.service.MenuService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

/**
 * @Author: xiaoli
 * @Date: 2022/11/27 --19:38
 * @Description:
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @ApiOperation(value = "查询所有菜单",notes = "可以根据菜单名进行模糊查询")
    public ResponseResult list(String status,String menuName){
        return menuService.selectAllMenu(status,menuName);
    }

    @PostMapping
    @ApiOperation(value = "新增菜单")
    public ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询菜单")
    public ResponseResult selectMenuById(@PathVariable("id") Long id){
        return menuService.selectMenuById(id);
    }

    @PutMapping
    @ApiOperation(value = "更新菜单")
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除菜单",notes = "不能删除有子菜单的菜单")
    public ResponseResult deleteMenu(@PathVariable("id") Long id){
        return menuService.deleteMenu(id);
    }

    @GetMapping("/treeselect")
    @ApiOperation(value = "获取菜单树接口")
    public ResponseResult selectTreeMenu(){
        return menuService.selectTreeMenu();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    @ApiOperation(value = "加载对应角色菜单树接口",notes = "通过id加载对应角色菜单树")
    public ResponseResult selectTreeMenuByRoleId(@PathVariable("id") Long id){
        return menuService.selectTreeMenuByRoleId(id);
    }
}
