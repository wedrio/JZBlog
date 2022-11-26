package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.TagListDto;
import com.weirdo.domain.entity.User;
import com.weirdo.domain.vo.PageVo;
import com.weirdo.enums.AppHttpCodeEnum;
import com.weirdo.exception.SystemException;
import com.weirdo.mapper.TagMapper;
import com.weirdo.domain.entity.Tag;
import com.weirdo.service.TagService;
import com.weirdo.utils.BeanCopyUtils;
import com.weirdo.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Tag> tags = page.getRecords();
        List<TagListDto> tagListDtos = BeanCopyUtils.copyBeanList(tags, TagListDto.class);
        PageVo pageVo = new PageVo(tagListDtos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        if (!StringUtils.hasText(tagListDto.getName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_TAG_NAME);
        }
        User user = SecurityUtils.getLoginUser().getUser();
        Tag tag = new Tag();
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        tag.setCreateBy(user.getId());
        tag.setCreateTime(new Date());
        tag.setUpdateBy(user.getId());
        tag.setUpdateTime(new Date());
        if (!StringUtils.hasText(tagListDto.getRemark())){
            tag.setRemark(tagListDto.getRemark());
        }
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delTag(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}

