package com.weirdo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/29 --13:44
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLinkDto {
    private Long id;
    private String name;
    private String description;
    private String logo;
    private String address;
    private String status;
}
