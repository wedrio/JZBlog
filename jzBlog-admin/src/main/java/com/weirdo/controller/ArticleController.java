package com.weirdo.controller;

import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.UpdateArticleDto;
import com.weirdo.domain.dto.ArticleListDto;
import com.weirdo.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xiaoli
 * @Date: 2022/11/27 --16:34
 * @Description:
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    @ApiOperation(value = "写博文")
    public ResponseResult add(@RequestBody UpdateArticleDto updateArticleDto){
        return articleService.add(updateArticleDto);
    }
    @GetMapping("/list")
    @ApiOperation(value = "后台文章列表")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){
        return articleService.articlePageList(pageNum,pageSize,articleListDto);
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询文章信息")
    public ResponseResult selectArticleById(@PathVariable("id")Long id){
        return articleService.selectArticleById(id);
    }
    @PutMapping
    @ApiOperation(value = "更新文章接口")
    public ResponseResult updateArticleById(@RequestBody UpdateArticleDto updateArticleDto){
        return articleService.updateArticleById(updateArticleDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除文章")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        return articleService.deleteArticleById(id);
    }
}
