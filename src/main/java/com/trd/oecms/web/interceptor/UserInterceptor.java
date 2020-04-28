package com.trd.oecms.web.interceptor;

import com.trd.oecms.model.LoginInfo;
import com.trd.oecms.utils.UserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tanruidong
 * @date 2020-04-07 12:17
 */
public class UserInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginInfo loginInfo = UserUtil.getCurrentLoginInfo();
        if (loginInfo == null){
            response.sendRedirect("/index");
            return false;
        }
        return true;
    }

}
