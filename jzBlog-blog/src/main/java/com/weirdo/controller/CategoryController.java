package com.weirdo.controller;



import com.weirdo.domain.ResponseResult;
import com.weirdo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 分类表(Category)表控制层
 *
 * @author xiaoli
 * @since 2022-11-05 15:57:34
 */
@RestController
@RequestMapping("category")
public class CategoryController  {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    /**
     *
     * @return
     */
    @RequestMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }

}

