package com.weirdo.controller;




import com.weirdo.domain.entity.Article;
import com.weirdo.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @RequestMapping("/test")
    public List<Article> test(){
        return articleService.list();
    }

}

