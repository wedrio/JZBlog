package com.weirdo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddRoleDto;
import com.weirdo.domain.dto.ChangeStatusDto;
import com.weirdo.domain.entity.Role;
import com.weirdo.domain.vo.UpdateRoleVo;

import java.util.List;

/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
public interface RoleService extends IService<Role> {

    List<String> queryRoleByUserId(Long id);

    /**
     * 分页查询角色列表（可通过状态查询，角色名模糊查询）
     * @return
     */
    ResponseResult selectAllPageRole(Integer pageNo, Integer pageSize, String roleName, String status);

    /**
     * 更改角色状态
     * @param changeStatusDto
     * @return
     */
    ResponseResult changeStatus(ChangeStatusDto changeStatusDto);

    /**
     * 添加角色
     * @param addRoleDto
     * @return
     */
    ResponseResult addRole(AddRoleDto addRoleDto);

    /**
     * 更新角色的角色信息回显
     * @param id
     * @return
     */
    ResponseResult selectRoleById(Long id);

    /**
     * 更新角色
     * @param updateRoleVo
     * @return
     */
    ResponseResult updateRole(UpdateRoleVo updateRoleVo);

    /**
     * 删除角色
     * @param id
     * @return
     */
    ResponseResult deleteRoleById(Long id);

    ResponseResult listAllRole();
}

