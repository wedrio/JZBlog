package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2022/11/28 --21:17
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleVo {
    private Long id;
    private String roleName;
    private String roleKey;
    private Integer roleSort;
    private String status;
    private String remark;
    private List<String> menuIds;
}
