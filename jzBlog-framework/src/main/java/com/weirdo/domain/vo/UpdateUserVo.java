package com.weirdo.domain.vo;

import com.weirdo.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2022/11/29 --16:36
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserVo {
    private List<String> roleIds;
    private List<Role> roles;
    private UpdateUserInfoVo user;
}
