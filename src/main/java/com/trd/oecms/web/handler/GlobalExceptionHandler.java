package com.trd.oecms.web.handler;

import com.trd.oecms.exception.AuthNotPassException;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  全局异常处理器
 * @author tanruidong
 * @date 2020-04-08 18:04
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理AuthNotPassException异常，实现权限控制导向错误页面
     * @param e AuthNotPassException（自定义的）
     * @return
     */
    @ExceptionHandler(AuthNotPassException.class)
    public ModelAndView toErrorPage(AuthNotPassException e){
        ModelAndView mv = new ModelAndView("errorPage");
        mv.addObject("msg", e.getMessage());
        mv.addObject("userName", UserUtil.getCurrentLoginInfo().getUserName());
        return mv;
    }

    /**
     * 处理前端传来的参数不在约束内的异常 (validation抛出的异常)
     * @param e ConstraintViolationException
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public JsonResult getErrorArgsInfo(ConstraintViolationException e){
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> collect = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        String delimitedString = StringUtils.collectionToDelimitedString(collect, "。", "【", "】");
        return JsonResult.error(delimitedString);
    }
}
