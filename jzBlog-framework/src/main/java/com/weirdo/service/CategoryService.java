package com.weirdo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddCategoryDto;
import com.weirdo.domain.dto.UpdateCategoryDto;
import com.weirdo.domain.entity.Category;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult selectAllPageCategory(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(AddCategoryDto addCategoryDto);

    ResponseResult selectCategoryById(Long id);

    ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto);

    ResponseResult deleteCategory(Long id);
}

