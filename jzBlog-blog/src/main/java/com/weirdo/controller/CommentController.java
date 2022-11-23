package com.weirdo.controller;


import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.entity.Comment;
import com.weirdo.service.CommentService;
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
public class CommentController {
    /**
     * 服务对象
     */
    @Resource
    private CommentService commentService;

    /**
     * 查询某个文章下的评论列表
     *
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/commentList")
    public ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.getCommentList(articleId, pageNum, pageSize);
    }

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    /**
     * 查询友链评论列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/linkCommentList")
    public ResponseResult getLinkCommentList(Integer pageNum,Integer pageSize){
        return commentService.getLinkComment(pageNum,pageSize);
    }

}

