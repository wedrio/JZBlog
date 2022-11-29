package com.weirdo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/27 --17:07
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDto {
    private String title;
    private String summary;
}
