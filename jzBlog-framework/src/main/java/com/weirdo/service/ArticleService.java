package com.weirdo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.entity.Article;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2022-11-05 15:57:30
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();
}

