package com.weirdo.filter;

import com.alibaba.fastjson.JSON;
import com.weirdo.domain.ResponseResult;
import com.weirdo.domain.entity.LoginUser;
import com.weirdo.enums.AppHttpCodeEnum;
import com.weirdo.utils.CacheService;
import com.weirdo.utils.JwtUtil;
import com.weirdo.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: xiaoli
 * @Date: 2022/11/20 --13:59
 * @Description:
 */
@Component
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private CacheService cacheService;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //没有token放行，说明是不带token的接口
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String userid = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse,JSON.toJSONString(result));
            return;
        }

        //获取redis中用户数据 然后存入SecurityContextHolder
        String s = cacheService.get("adminLogin:"+userid);
        LoginUser loginUser = JSON.parseObject(s, LoginUser.class);
        if (Objects.isNull(loginUser)){
            //说明登录过期 提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse,JSON.toJSONString(result));
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginUser,null,null));
        //放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
