package com.weirdo.controller;



import com.weirdo.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 标签(Tag)表控制层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@RestController
@RequestMapping("tag")
public class TagController  {
    /**
     * 服务对象
     */
    @Resource
    private TagService tagService;


}

