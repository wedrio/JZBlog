package com.weirdo.domain.vo;

import com.weirdo.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2022/11/25 --20:39
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {
    private List<MenuVo> menus;
}
