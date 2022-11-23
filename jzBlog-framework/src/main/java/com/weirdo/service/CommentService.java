package com.weirdo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.entity.Comment;
import com.weirdo.domain.vo.CommentListVo;

import java.util.List;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
public interface CommentService extends IService<Comment> {

    ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize);

    List<CommentListVo> getChildren(Long id);

    ResponseResult addComment(Comment comment);

    ResponseResult getLinkComment(Integer pageNum, Integer pageSize);
}

