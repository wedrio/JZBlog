package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.constants.SystemConstants;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddLinkDto;
import com.weirdo.domain.dto.UpdateLinkDto;
import com.weirdo.domain.dto.UpdateLinkStatusDto;
import com.weirdo.domain.vo.AdminLinkListPageVo;
import com.weirdo.domain.vo.LinkListVo;
import com.weirdo.domain.vo.PageVo;
import com.weirdo.mapper.LinkMapper;
import com.weirdo.domain.entity.Link;
import com.weirdo.service.LinkService;
import com.weirdo.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> linkQueryWrapper = new LambdaQueryWrapper<>();
        linkQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_NORMAN);
        List<Link> links = list(linkQueryWrapper);
        List<LinkListVo> linkListVos = BeanCopyUtils.copyBeanList(links, LinkListVo.class);
        return ResponseResult.okResult(linkListVos);
    }

    @Override
    public ResponseResult getAllLink(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> linkQueryWrapper = new LambdaQueryWrapper<>();
        linkQueryWrapper.eq(StringUtils.hasText(status),Link::getStatus,status);
        linkQueryWrapper.like(StringUtils.hasText(name),Link::getName,name);
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page,linkQueryWrapper);
        List<Link> links = page.getRecords();
        List<AdminLinkListPageVo> adminLinkListPageVos = BeanCopyUtils.copyBeanList(links, AdminLinkListPageVo.class);
        return ResponseResult.okResult(new PageVo(adminLinkListPageVos,page.getTotal()));
    }

    @Override
    public ResponseResult addLink(AddLinkDto addLinkDto) {
        Link link = BeanCopyUtils.copyBean(addLinkDto, Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectLinkById(Long id) {
        Link link = getById(id);
        AdminLinkListPageVo linkListPageVo = BeanCopyUtils.copyBean(link, AdminLinkListPageVo.class);
        return ResponseResult.okResult(linkListPageVo);
    }

    @Override
    public ResponseResult updateLink(UpdateLinkDto updateLinkDto) {
        Link link = BeanCopyUtils.copyBean(updateLinkDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeLinkStatus(UpdateLinkStatusDto updateLinkStatusDto) {
        UpdateWrapper<Link> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",updateLinkStatusDto.getId());
        updateWrapper.set("status",updateLinkStatusDto.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult();
    }
}

