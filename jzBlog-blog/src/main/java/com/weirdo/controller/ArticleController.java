package com.weirdo.controller;




import com.weirdo.domain.ResponseResult;
import com.weirdo.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

/**
 * 文章表(Article)表控制层
 *
 * @author makejava
 * @since 2022-11-05 15:57:28
 */
@RestController
@RequestMapping("article")
public class ArticleController{
    /**
     * 服务对象
     */
    @Resource
    private ArticleService articleService;

    /**
     * 查询热度前10文章
     * @return
     */
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long articleId){
        return articleService.getArticleDetail(articleId);
    }
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long articleId){
        return articleService.updateViewCount(articleId);
    }
}

