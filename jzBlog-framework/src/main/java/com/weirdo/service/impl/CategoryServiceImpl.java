package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.constants.SystemConstants;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddCategoryDto;
import com.weirdo.domain.dto.UpdateCategoryDto;
import com.weirdo.domain.entity.Article;
import com.weirdo.domain.vo.CategoryListVo;
import com.weirdo.domain.vo.CategoryVo;
import com.weirdo.domain.vo.PageVo;
import com.weirdo.mapper.CategoryMapper;
import com.weirdo.domain.entity.Category;
import com.weirdo.service.ArticleService;
import com.weirdo.service.CategoryService;
import com.weirdo.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author xiaoli
 * @since 2022-11-05 15:57:34
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    private ArticleService articleService;



    @Override
    public ResponseResult getCategoryList() {
        //设置查询条件
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //查询文章列表
        List<Article> articleList = articleService.list(articleWrapper);
        //对文章类型id去重
        Set<Long> categoryIds = articleList.stream().map(article -> article.getCategoryId()).collect(Collectors.toSet());
        //查询分类列表
        List<Category> categories = listByIds(categoryIds);
        //转换成stream流过滤出
        categories.stream().filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus())).collect(Collectors.toList());
        //使用Vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult selectAllPageCategory(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name),Category::getName,name);
        queryWrapper.eq(StringUtils.hasText(status),Category::getStatus,status);
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<Category> records = page.getRecords();
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(records, CategoryListVo.class);
        return ResponseResult.okResult(new PageVo(categoryListVos,page.getTotal()));
    }

    @Override
    public ResponseResult addCategory(AddCategoryDto addCategoryDto) {
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectCategoryById(Long id) {
        Category category = getById(id);
        CategoryListVo categoryListVo = BeanCopyUtils.copyBean(category, CategoryListVo.class);
        return ResponseResult.okResult(categoryListVo);
    }

    @Override
    public ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto) {
        Category category = BeanCopyUtils.copyBean(updateCategoryDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}

