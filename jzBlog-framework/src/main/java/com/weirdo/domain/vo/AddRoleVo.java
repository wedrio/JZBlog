package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2022/11/28 --20:11
 * @Description: 修改角色，角色信息回显实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleVo {
    private Long id;
    private String roleName;
    private String roleKey;
    private Integer roleSort;
    private String status;
    private String remark;
}
