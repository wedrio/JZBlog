package com.weirdo.controller;

import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddLinkDto;
import com.weirdo.domain.dto.UpdateLinkDto;
import com.weirdo.domain.dto.UpdateLinkStatusDto;
import com.weirdo.service.LinkService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xiaoli
 * @Date: 2022/11/29 --13:06
 * @Description:
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/list")
    @ApiOperation(value = "分页查询友链")
    public ResponseResult list(Integer pageNum,Integer pageSize,String name,String status){
        return linkService.getAllLink(pageNum,pageSize,name,status);
    }
    @PostMapping
    @ApiOperation(value = "新增友链")
    public ResponseResult addLink(@RequestBody AddLinkDto addLinkDto){
        return linkService.addLink(addLinkDto);
    }
    @GetMapping("/{id}")
    @ApiOperation("通过id查询友链")
    public ResponseResult selectLinkById(@PathVariable("id") Long id){
        return linkService.selectLinkById(id);
    }
    @PutMapping
    @ApiOperation(value = "修改友链")
    public ResponseResult updateLink(@RequestBody UpdateLinkDto updateLinkDto){
        return linkService.updateLink(updateLinkDto);
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除友链")
    public ResponseResult deleteLink(@PathVariable("id")Long id){
        return linkService.deleteLink(id);
    }
    @PutMapping("/changeLinkStatus")
    @ApiOperation(value = "改变友链状态")
    public ResponseResult changeLinkStatus(@RequestBody UpdateLinkStatusDto updateLinkStatusDto){
        return linkService.changeLinkStatus(updateLinkStatusDto);
    }
}
