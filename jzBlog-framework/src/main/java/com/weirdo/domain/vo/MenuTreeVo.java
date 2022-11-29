package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2022/11/27 --22:02
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeVo {
    private Long id;
    private String label;
    private Long parentId;
    private List<MenuTreeVo> children;
}
