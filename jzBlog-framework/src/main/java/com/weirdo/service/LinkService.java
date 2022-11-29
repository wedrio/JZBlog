package com.weirdo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.dto.AddLinkDto;
import com.weirdo.domain.dto.UpdateLinkDto;
import com.weirdo.domain.dto.UpdateLinkStatusDto;
import com.weirdo.domain.entity.Link;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult getAllLink(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(AddLinkDto addLinkDto);

    ResponseResult selectLinkById(Long id);

    ResponseResult updateLink(UpdateLinkDto updateLinkDto);

    ResponseResult deleteLink(Long id);

    ResponseResult changeLinkStatus(UpdateLinkStatusDto updateLinkStatusDto);
}

