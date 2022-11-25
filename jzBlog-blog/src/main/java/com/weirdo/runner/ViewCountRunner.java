package com.weirdo.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weirdo.domain.entity.Article;
import com.weirdo.mapper.ArticleMapper;
import com.weirdo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: xiaoli
 * @Date: 2022/11/24 --16:38
 * @Description:
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("准备初始化文章浏览量......");
        //查询所有文章的id和浏览量并存为一个map
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream().collect(Collectors.toMap(article -> article.getId().toString(), article ->
            article.getViewCount().intValue()));
        //存储到redis中
        redisCache.setCacheMap("article:viewCount",viewCountMap);
        System.out.println("文章浏览量初始化完毕！");
    }
}
