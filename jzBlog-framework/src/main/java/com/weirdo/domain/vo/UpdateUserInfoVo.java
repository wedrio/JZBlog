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
public class UpdateUserInfoVo {
    private String avatar;
    private String email;
    private Long id;
    private String phonenumber;
    private String nickName;
    private String sex;
    private String status;
}
