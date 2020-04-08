package com.trd.oecms.web.handler;

import com.trd.oecms.exception.AuthNotPassException;
import com.trd.oecms.utils.UserUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 处理AuthNotPassException异常，实现权限控制导向错误页面
 * @author tanruidong
 * @date 2020-04-08 18:04
 */
@ControllerAdvice
public class AuthNotPassExceptionHandler {

    @ExceptionHandler(AuthNotPassException.class)
    public String toErrorPage(Model model, AuthNotPassException e){
        model.addAttribute("msg", e.getMessage());
        model.addAttribute("loginInfo", UserUtil.getCurrentLoginInfo());
        return "errorPage";
    }
}
