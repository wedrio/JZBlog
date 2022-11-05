package com.weirdo.controller;




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
public class CommentController  {
    /**
     * 服务对象
     */
    @Resource
    private CommentService commentService;

    }

