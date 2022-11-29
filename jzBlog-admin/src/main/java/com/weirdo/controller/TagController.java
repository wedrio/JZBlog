package com.weirdo.controller;

import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.TagDto;
import com.weirdo.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xiaoli
 * @Date: 2022/11/25 --16:43
 * @Description:
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagDto tagDto){
        return tagService.pageTagList(pageNum,pageSize, tagDto);
    }
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
    @PostMapping
    @ApiOperation(value = "新增标签")
    public ResponseResult addTag(@RequestBody TagDto tagDto){
        return tagService.addTag(tagDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除标签")
    public ResponseResult delTag(@PathVariable(value = "id") Long id){
        return tagService.delTag(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取标签")
    public ResponseResult getTag(@PathVariable(value = "id") Long id){
        return tagService.getTag(id);
    }
    @PutMapping
    @ApiOperation(value = "更新标签")
    public ResponseResult updateTag(@RequestBody TagDto tagDto){
        return tagService.updateTag(tagDto);
    }
}
