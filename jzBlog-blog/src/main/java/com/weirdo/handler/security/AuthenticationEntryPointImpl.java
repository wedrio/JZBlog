package com.weirdo.handler.security;

import com.alibaba.fastjson.JSON;
import com.weirdo.domain.ResponseResult;
import com.weirdo.enums.AppHttpCodeEnum;
import com.weirdo.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: xiaoli
 * @Date: 2022/11/20 --15:02
 * @Description: 认证失败处理器
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //InsufficientAuthenticationException
        //BadCredentialsException
        ResponseResult result = null;
        if (e instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else if (e instanceof BadCredentialsException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或者授权失败");
        }
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
