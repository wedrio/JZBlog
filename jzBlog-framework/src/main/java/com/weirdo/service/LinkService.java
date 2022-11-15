package com.weirdo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.entity.Link;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

