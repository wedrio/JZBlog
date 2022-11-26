package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2022/11/25 --19:08
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {

    List<String> permissions;

    List<String> roles;

    UserInfoVo user;
}
