package com.weirdo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.constants.SystemConstants;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.vo.LinkListVo;
import com.weirdo.mapper.LinkMapper;
import com.weirdo.domain.entity.Link;
import com.weirdo.service.LinkService;
import com.weirdo.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

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
}

