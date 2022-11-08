package com.weirdo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.constants.SystemConstants;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.entity.Article;
import com.weirdo.domain.vo.HotArticleListVo;
import com.weirdo.mapper.ArticleMapper;
import com.weirdo.service.ArticleService;
import com.weirdo.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2022-11-05 15:57:31
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public ResponseResult hotArticleList() {

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是已经发布的文章
        //        1. 其中Article::getStatus的意思就相当于：
        //        1.1 实例化一个Article对象
        //        1.2 调用对象Article的get方法，这里调用的是getStatus:
        //        article.getStatus();
        //        2.eq方法相当于赋值“=”
        //        即将Status的值为参数状态(0为发布，1为草稿)，注意此时使用的是get方法而不是set方法
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page<>(1, 10);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        List<HotArticleListVo> vo = BeanCopyUtils.BeanList(articles,HotArticleListVo.class);

        return ResponseResult.okResult(vo);
    }
}

