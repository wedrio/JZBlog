package com.weirdo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.domain.entity.Article;
import com.weirdo.mapper.ArticleMapper;
import com.weirdo.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2022-11-05 15:57:31
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}

