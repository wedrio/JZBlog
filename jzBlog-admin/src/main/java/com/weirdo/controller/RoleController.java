package com.weirdo.controller;

import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddRoleDto;
import com.weirdo.domain.dto.ChangeStatusDto;
import com.weirdo.domain.vo.UpdateRoleVo;
import com.weirdo.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: xiaoli
 * @Date: 2022/11/27 --20:42
 * @Description:
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @ApiOperation(value = "查询角色列表")
    public ResponseResult list(Integer pageNum,Integer pageSize,String roleName,String status){
        return roleService.selectAllPageRole(pageNum,pageSize,roleName,status);
    }

    @PutMapping("/changeStatus")
    @ApiOperation(value = "更改角色状态")
    public ResponseResult changeStatus(@RequestBody ChangeStatusDto changeStatusDto){
        return roleService.changeStatus(changeStatusDto);
    }

    @PostMapping
    @ApiOperation(value = "新增角色")
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto){
        return roleService.addRole(addRoleDto);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询角色信息")
    public ResponseResult selectRoleById(@PathVariable("id") Long id){
        return roleService.selectRoleById(id);
    }

    @PutMapping
    @ApiOperation(value = "更新角色信息")
    public ResponseResult updateRole(@RequestBody UpdateRoleVo updateRoleVo){
        return roleService.updateRole(updateRoleVo);
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除角色")
    public ResponseResult deleteRoleById(@PathVariable("id")Long id){
        return roleService.deleteRoleById(id);
    }
    @GetMapping("/listAllRole")
    @ApiOperation(value = "查询角色列表")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }
}
