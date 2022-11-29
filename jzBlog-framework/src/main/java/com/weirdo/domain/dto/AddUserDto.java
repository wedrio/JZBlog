package com.weirdo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2022/11/29 --15:58
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDto {
    private String userName;
    private String nickName;
    private String password;
    private String phonenumber;
    private String email;
    private String sex;
    private String status;
    private List<String> roleIds;
}
