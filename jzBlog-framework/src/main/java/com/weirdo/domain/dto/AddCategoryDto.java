package com.weirdo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/29 --14:47
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCategoryDto {
    private String name;
    private String description;
    private String status;
}
