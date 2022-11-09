package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/9 --14:28
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    //id
    private Long id;
    //分类名称
    private String name;
}
