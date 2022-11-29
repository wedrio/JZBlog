package com.weirdo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/29 --13:28
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLinkDto {
    private String name;
    private String description;
    private String logo;
    private String address;
    private String status;
}
