package com.weirdo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.mapper.CategoryMapper;
import com.weirdo.domain.entity.Category;
import com.weirdo.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}

