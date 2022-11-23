package com.weirdo.service;

import com.weirdo.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: xiaoli
 * @Date: 2022/11/23 --21:33
 * @Description:
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
