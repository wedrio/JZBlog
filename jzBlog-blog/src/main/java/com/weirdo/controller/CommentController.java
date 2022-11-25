package com.weirdo.controller;


import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddCommentDto;
import com.weirdo.domain.entity.Comment;
import com.weirdo.service.CommentService;
import com.weirdo.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 评论表(Comment)表控制层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@RestController
@RequestMapping("comment")
@Api(tags = "评论",description = "评论相关接口")
public class CommentController {

    @Resource
    private CommentService commentService;


    @GetMapping("/commentList")
    @ApiOperation(value = "查询文章下评论列表",notes = "获取某文章下一页评论")
    public ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.getCommentList(articleId, pageNum, pageSize);
    }


    @PostMapping
    @ApiOperation(value = "添加评论")
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }


    @GetMapping("/linkCommentList")
    @ApiOperation(value = "查询友链评论列表",notes = "查询友链的一页评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    })
    public ResponseResult getLinkCommentList(Integer pageNum,Integer pageSize){
        return commentService.getLinkComment(pageNum,pageSize);
    }

}

