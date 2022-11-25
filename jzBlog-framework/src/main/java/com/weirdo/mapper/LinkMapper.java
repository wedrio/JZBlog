package com.weirdo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weirdo.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;

/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}

