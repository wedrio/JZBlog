package com.weirdo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.TagDto;
import com.weirdo.domain.entity.Tag;

/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagDto tagDto);

    ResponseResult addTag(TagDto tagDto);

    ResponseResult delTag(Long id);

    ResponseResult updateTag(TagDto tagDto);

    ResponseResult getTag(Long id);

    ResponseResult listAllTag();
}

