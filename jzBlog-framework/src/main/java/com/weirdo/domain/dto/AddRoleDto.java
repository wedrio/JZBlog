package com.weirdo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2022/11/28 --15:20
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleDto {
    private String roleName;
    private String roleKey;
    private Integer roleSort;
    private String status;
    private String remark;
    private List<String> menuIds;
}
