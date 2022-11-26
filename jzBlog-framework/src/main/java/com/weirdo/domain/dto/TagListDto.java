package com.weirdo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @Author: xiaoli
 * @Date: 2022/11/26 --21:32
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {
    private Long id;
    private String name;
    private String remark;
}
