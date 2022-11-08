package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/6 --13:32
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleListVo {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
