package com.weirdo.controller;

import com.weirdo.domain.ResponseResult;
import com.weirdo.enums.AppHttpCodeEnum;
import com.weirdo.exception.SystemException;
import com.weirdo.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: xiaoli
 * @Date: 2022/11/23 --21:31
 * @Description:
 */
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile img){
        try{
            return uploadService.uploadImg(img);
        }catch (Exception e){
            throw new RuntimeException("文件上传失败");
        }
    }
}
