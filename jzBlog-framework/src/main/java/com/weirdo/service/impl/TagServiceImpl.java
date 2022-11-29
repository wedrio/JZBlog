package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.TagDto;
import com.weirdo.domain.entity.User;
import com.weirdo.domain.vo.PageVo;
import com.weirdo.domain.vo.TagVo;
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
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagDto tagDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagDto.getName()),Tag::getName, tagDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagDto.getRemark()),Tag::getRemark, tagDto.getRemark());
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Tag> tags = page.getRecords();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        PageVo pageVo = new PageVo(tagVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagDto tagDto) {
        if (!StringUtils.hasText(tagDto.getName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_TAG_NAME);
        }
        User user = SecurityUtils.getLoginUser().getUser();
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        tag.setRemark(tagDto.getRemark());
        tag.setCreateBy(user.getId());
        tag.setCreateTime(new Date());
        tag.setUpdateBy(user.getId());
        tag.setUpdateTime(new Date());
        if (!StringUtils.hasText(tagDto.getRemark())){
            tag.setRemark(tagDto.getRemark());
        }
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delTag(Long id) {
        TagMapper mapper = getBaseMapper();
        mapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateTag(TagDto tagDto) {
        if (!StringUtils.hasText(tagDto.getName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_TAG_NAME);
        }
        User user = SecurityUtils.getLoginUser().getUser();
        LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Tag::getId,tagDto.getId());
        updateWrapper.set(Tag::getUpdateBy,user.getId());
        updateWrapper.set(Tag::getUpdateTime,new Date());
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        update(tag,updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTag(Long id) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getId,id);
        Tag tag = getOne(queryWrapper);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult listAllTag() {
        List<Tag> tags = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}

