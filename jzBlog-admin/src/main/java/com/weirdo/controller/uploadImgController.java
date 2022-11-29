package com.weirdo.controller;

import com.weirdo.domain.ResponseResult;
import com.weirdo.service.UploadService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: xiaoli
 * @Date: 2022/11/27 --15:16
 * @Description:
 */
@RestController
public class uploadImgController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation(value = "图片上传")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile img){
        try{
            return uploadService.uploadImg(img);
        }catch (Exception e){
            throw new RuntimeException("文件上传失败");
        }
    }
}
