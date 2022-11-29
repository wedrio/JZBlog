package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddRoleDto;
import com.weirdo.domain.dto.ChangeStatusDto;
import com.weirdo.domain.entity.RoleMenu;
import com.weirdo.domain.vo.AddRoleVo;
import com.weirdo.domain.vo.PageVo;
import com.weirdo.domain.vo.RoleListVo;
import com.weirdo.domain.vo.UpdateRoleVo;
import com.weirdo.mapper.RoleMapper;
import com.weirdo.domain.entity.Role;
import com.weirdo.service.RoleMenuService;
import com.weirdo.service.RoleService;
import com.weirdo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> queryRoleByUserId(Long id) {
        return roleMapper.queryRoleByUserId(id);
    }

    @Override
    public ResponseResult selectAllPageRole(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status),Role::getStatus,status);
        queryWrapper.like(StringUtils.hasText(roleName),Role::getRoleName,roleName);
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Role> roles = page.getRecords();
        List<RoleListVo> listVos = BeanCopyUtils.copyBeanList(roles, RoleListVo.class);
        return ResponseResult.okResult(new PageVo(listVos,page.getTotal()));
    }

    @Override
    public ResponseResult changeStatus(ChangeStatusDto changeStatusDto) {
        UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",changeStatusDto.getId());
        updateWrapper.set("status",changeStatusDto.getStatus());
        update(null,updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        //先新增角色
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);
        //再添加角色和菜单中间表关联关系
        List<String> menuIds = addRoleDto.getMenuIds();
        for (String menuId : menuIds) {
            roleMenuService.save(new RoleMenu(role.getId(),Long.parseLong(menuId)));
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectRoleById(Long id) {
        //通过id查询到角色的全部信息
        Role role = getById(id);
        AddRoleVo addRoleVo = BeanCopyUtils.copyBean(role, AddRoleVo.class);
        return ResponseResult.okResult(addRoleVo);
    }

    @Override
    public ResponseResult updateRole(UpdateRoleVo updateRoleVo) {
        //先更新角色信息
        Role role = BeanCopyUtils.copyBean(updateRoleVo, Role.class);
        updateById(role);
        //通过角色id删除掉中间表关联信息
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,role.getId());
        roleMenuService.remove(queryWrapper);
        //添加中间表信息
        List<String> menuIds = updateRoleVo.getMenuIds();
        for (String menuId : menuIds) {
            roleMenuService.save(new RoleMenu(role.getId(),Long.parseLong(menuId)));
        }
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteRoleById(Long id) {
        //先删除角色信息
        removeById(id);
        //再删除对应中间表的字段
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        roleMenuService.remove(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        List<Role> list = list();
        return ResponseResult.okResult(list);
    }

}

