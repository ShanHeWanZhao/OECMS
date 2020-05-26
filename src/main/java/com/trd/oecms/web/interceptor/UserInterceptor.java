package com.trd.oecms.web.interceptor;

import com.trd.oecms.function.ThrowableSupplier;
import com.trd.oecms.model.LoginInfo;
import com.trd.oecms.utils.UserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author tanruidong
 * @date 2020-04-07 12:17
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

    /**
     * login intercept , get {@link LoginInfo} in {@link HttpSession} <p/>
     * the user is null, redirect to "/index" and return false  <br/>
     * the user is not null, return true
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param handler handler instance
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return Optional.ofNullable(UserUtil.getCurrentLoginInfo()).
                map(loginInfo -> true).
                orElseGet(ThrowableSupplier.throwableSupplierWrapper(() -> {
                    response.sendRedirect("/index");
                    return false;
                }));
    }

}
