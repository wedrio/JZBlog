package com.weirdo.controller;




import com.weirdo.domain.ResponseResult;
import com.weirdo.service.LinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 友链(Link)表控制层
 *
 * @author makejava
 * @since 2022-11-05 15:57:34
 */
@RestController
@RequestMapping("link")
public class LinkController  {
    /**
     * 服务对象
     */
    @Resource
    private LinkService linkService;

    /**
     * 获取所有友链
     * @return
     */
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}

