package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.constants.SystemConstants;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.entity.User;
import com.weirdo.domain.vo.CommentListVo;
import com.weirdo.domain.vo.PageVo;
import com.weirdo.mapper.CommentMapper;
import com.weirdo.domain.entity.Comment;
import com.weirdo.service.CommentService;
import com.weirdo.service.UserService;
import com.weirdo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;


    @Override
    public ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize) {
        //查询文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //查询文章的条件
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_COMMENT);
        //分页对象
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        //获取查询的记录
        List<Comment> commentList = page.getRecords();
//        List<CommentListVo> commentListVos = BeanCopyUtils.copyBeanList(commentList, CommentListVo.class);
        List<CommentListVo> commentListVos = toCommentVoList(commentList);
        //查询所有根评论对应的子评论集合，并且赋值给对应的属性

        for (CommentListVo listVo : commentListVos) {
            List<CommentListVo> children = getChildren(listVo.getId());
            listVo.setChildren(children);
        }
        PageVo vo = new PageVo(commentListVos,commentListVos.stream().count());
        return ResponseResult.okResult(vo);
    }


    @Override
    public List<CommentListVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getToCommentId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> commentList = list(queryWrapper);
        List<CommentListVo> commentListVos = toCommentVoList(commentList);
        return commentListVos;
    }


    /**
     * 将评论实体转换成评论视图实体
     * @param commentList
     * @return
     */
    private List<CommentListVo> toCommentVoList(List<Comment> commentList) {
        List<CommentListVo> vos = BeanCopyUtils.copyBeanList(commentList, CommentListVo.class);
        for (CommentListVo vo : vos) {
            User user = userService.getById(vo.getCreateBy());
            vo.setUsername(user.getNickName());
            //判断toCommentUserId查询用户的昵称并赋值
            //要toCommentUserId不为-1才进行查询
            if (vo.getToCommentUserId()!=-1) {
                vo.setToCommentUserName(userService.getById(vo.getToCommentUserId()).getUserName());
            }
        }
        return vos;
    }
}

