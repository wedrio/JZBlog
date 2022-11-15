package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: xiaoli
 * @Date: 2022/11/9 --18:17
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String content;
    private Date createTime;
    private String isComment;
    private String title;
    private Long viewCount;
}
