package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2022/11/28 --20:34
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectMenuTreeVo {
    private List<MenuTreeVo> menus;
    private List<String> checkedKeys;
}
