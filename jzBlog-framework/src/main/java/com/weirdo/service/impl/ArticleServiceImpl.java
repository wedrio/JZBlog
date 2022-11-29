package com.weirdo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.constants.SystemConstants;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.UpdateArticleDto;
import com.weirdo.domain.dto.ArticleListDto;
import com.weirdo.domain.entity.Article;
import com.weirdo.domain.entity.ArticleTag;
import com.weirdo.domain.vo.*;
import com.weirdo.enums.AppHttpCodeEnum;
import com.weirdo.exception.SystemException;
import com.weirdo.mapper.ArticleMapper;
import com.weirdo.service.ArticleService;
import com.weirdo.service.ArticleTagService;
import com.weirdo.service.CategoryService;
import com.weirdo.utils.BeanCopyUtils;
import com.weirdo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2022-11-05 15:57:31
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;

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
        List<HotArticleListVo> vo = BeanCopyUtils.copyBeanList(articles,HotArticleListVo.class);

        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询wrapper
        LambdaQueryWrapper<Article> articleListWrapper = new LambdaQueryWrapper<>();
        //文章状态
        articleListWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //判断是否传入分类id
        articleListWrapper.eq(Objects.nonNull(categoryId) && categoryId>0,Article::getCategoryId,categoryId);
        //通过置顶关键字排序
        articleListWrapper.orderByDesc(Article::getIsTop);
        //创建page对象，分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page,articleListWrapper);
        List<Article> articleList = page.getRecords();
        //stream流分类id
        articleList.stream().map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName())).collect(Collectors.toList());

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);

        return ResponseResult.okResult(new PageVo(articleListVos,page.getTotal()));
    }

    @Override
    public ResponseResult getArticleDetail(Long articleId) {
        //通过id查询到文章
        Article article = getById(articleId);
        //获取到对应的分类名称
        String categoryName = categoryService.getById(article.getCategoryId()).getName();
        article.setCategoryName(categoryName);
        //浏览量从redis查询
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        Long viewCount = viewCountMap.get(articleId.toString()).longValue();
        article.setViewCount(viewCount);
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long articleId) {
        //每次新增都是更新redis中的数据
        redisCache.incrementCacheMapValue("article:viewCount",articleId.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(UpdateArticleDto updateArticleDto) {
        //校验输入内容
        if (!StringUtils.hasText(updateArticleDto.getTitle())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_ARTICLE_TITLE);
        }
        if (!StringUtils.hasText(updateArticleDto.getSummary())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_ARTICLE_SUMMARY);
        }
        if (!StringUtils.hasText(updateArticleDto.getThumbnail())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_ARTICLE_IMG);
        }
        if (!StringUtils.hasText(updateArticleDto.getContent())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_ARTICLE_CONTENT);
        }


        //先添加文章
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        save(article);
        List<ArticleTag> articleTags = updateArticleDto.getTags().stream().map(tagId -> new ArticleTag(article.getId(), tagId)).collect(Collectors.toList());
        //再添加博客和标签的关联关系
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }



    @Override
    public ResponseResult articlePageList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(articleListDto.getTitle()),Article::getTitle,articleListDto.getTitle());
        queryWrapper.like(StringUtils.hasText(articleListDto.getSummary()),Article::getSummary,articleListDto.getSummary());
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        List<ArticlePageListVo> articlePageListVos = BeanCopyUtils.copyBeanList(articles, ArticlePageListVo.class);
        return ResponseResult.okResult(new PageVo(articlePageListVos,page.getTotal()));
    }

    @Override
    public ResponseResult selectArticleById(Long id) {
        Article article = getById(id);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,article.getId());
        List<ArticleTag> articleTags = articleTagService.list(queryWrapper);
        List<String> tags = articleTags.stream().map(articleTag -> articleTag.getTagId().toString()).collect(Collectors.toList());
        ArticleUpdateVo articleUpdateVo = BeanCopyUtils.copyBean(article, ArticleUpdateVo.class);
        articleUpdateVo.setTags(tags);
        return ResponseResult.okResult(articleUpdateVo);
    }

    @Override
    @Transactional
    public ResponseResult updateArticleById(UpdateArticleDto updateArticleDto) {
        //先更新文章表
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        updateById(article);
        //然后删除文章初始标签（物理删除）
        BaseMapper<ArticleTag> baseMapper = articleTagService.getBaseMapper();
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,article.getId());
        baseMapper.delete(queryWrapper);
        //再添加文章新的标签
        List<ArticleTag> articleTags = updateArticleDto.getTags().stream().map(tagId -> new ArticleTag(article.getId(), tagId)).collect(Collectors.toList());

        //再添加博客和标签的关联关系
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticleById(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}

