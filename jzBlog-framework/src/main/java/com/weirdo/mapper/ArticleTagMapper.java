package com.weirdo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weirdo.domain.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-05 15:57:32
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}

