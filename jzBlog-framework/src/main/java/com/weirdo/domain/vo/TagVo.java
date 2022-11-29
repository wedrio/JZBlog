package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/27 --15:39
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    private Long id;
    private String name;
    private String remark;
}
