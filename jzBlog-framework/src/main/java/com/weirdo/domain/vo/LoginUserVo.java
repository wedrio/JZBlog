package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/20 --10:55
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo {
    private String token;
    private UserInfoVo userInfoVo;
}
