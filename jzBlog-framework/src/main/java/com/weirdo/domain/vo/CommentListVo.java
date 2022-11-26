package com.weirdo.domain.vo;

import com.weirdo.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2022/11/15 --15:27
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListVo {
    private Long id;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //所回复的目标评论的username
    private String toCommentUserName;
    //回复目标评论id
    private Long toCommentId;

    private String username;

    private Long createBy;

    private Date createTime;

    private List<CommentListVo> children;
}
