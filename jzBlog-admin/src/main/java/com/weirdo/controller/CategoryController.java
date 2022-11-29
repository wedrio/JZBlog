package com.weirdo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddCategoryDto;
import com.weirdo.domain.dto.UpdateCategoryDto;
import com.weirdo.domain.entity.Category;
import com.weirdo.domain.vo.ExcelCategoryVo;
import com.weirdo.enums.AppHttpCodeEnum;
import com.weirdo.service.CategoryService;
import com.weirdo.utils.BeanCopyUtils;
import com.weirdo.utils.WebUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2022/11/27 --15:00
 * @Description:
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("/listAllCategory")
    @ApiOperation(value = "到处excel文件")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        //设置下载文件的请求头
        try {
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            // 这里需要设置不关闭流
            List<Category> categoryList = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryList, ExcelCategoryVo.class);
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            e.printStackTrace();
            //出现了异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response,JSON.toJSONString(result));
        }
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页获取分类列表")
    public ResponseResult list(Integer pageNum,Integer pageSize,String name,String status){
        return categoryService.selectAllPageCategory(pageNum,pageSize,name,status);
    }
    @PostMapping
    @ApiOperation(value = "新增分类")
    public ResponseResult addCategory(@RequestBody AddCategoryDto addCategoryDto){
        return categoryService.addCategory(addCategoryDto);
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询分类")
    public ResponseResult selectCategoryById(@PathVariable("id") Long id){
        return categoryService.selectCategoryById(id);
    }
    @PutMapping
    @ApiOperation(value = "修改分类")
    public ResponseResult updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto){
        return categoryService.updateCategory(updateCategoryDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除分类")
    public ResponseResult deleteCategory(@PathVariable("id") Long id){
        return categoryService.deleteCategory(id);
    }
}
