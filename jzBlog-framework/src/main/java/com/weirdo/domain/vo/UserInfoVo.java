package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/20 --10:56
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {
    private String avatar;
    private String email;
    private Long id;
    private String nickName;
    private String sex;
}
