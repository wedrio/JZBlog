package com.weirdo.controller;




import com.weirdo.service.ArticleTagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 文章标签关联表(ArticleTag)表控制层
 *
 * @author makejava
 * @since 2022-11-05 15:57:31
 */
@RestController
@RequestMapping("articleTag")
public class ArticleTagController{
    /**
     * 服务对象
     */
    @Resource
    private ArticleTagService articleTagService;

    }

