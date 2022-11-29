package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/29 --14:44
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListVo {
    private Long id;
    private String name;
    private String status;
    private String description;
}
