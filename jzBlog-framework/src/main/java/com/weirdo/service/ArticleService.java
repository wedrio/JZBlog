package com.weirdo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.UpdateArticleDto;
import com.weirdo.domain.dto.ArticleListDto;
import com.weirdo.domain.entity.Article;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2022-11-05 15:57:30
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long articleId);

    ResponseResult updateViewCount(Long articleId);

    ResponseResult add(UpdateArticleDto updateArticleDto);

    ResponseResult articlePageList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    ResponseResult selectArticleById(Long id);

    ResponseResult updateArticleById(UpdateArticleDto updateArticleDto);

    ResponseResult deleteArticleById(Long id);
}

