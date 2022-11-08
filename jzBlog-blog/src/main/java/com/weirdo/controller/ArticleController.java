package com.weirdo.controller;




import com.weirdo.domain.ResponseResult;
import com.weirdo.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

}

