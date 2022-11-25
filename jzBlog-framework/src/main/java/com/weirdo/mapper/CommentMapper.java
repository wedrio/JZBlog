package com.weirdo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weirdo.domain.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}

