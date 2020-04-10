package com.trd.oecms.web.handler;

import com.trd.oecms.exception.AuthNotPassException;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理AuthNotPassException异常，实现权限控制导向错误页面
     * @param model
     * @param e AuthNotPassException（自定义的）
     * @return
     */
    @ExceptionHandler(AuthNotPassException.class)
    public String toErrorPage(Model model, AuthNotPassException e){
        model.addAttribute("msg", e.getMessage());
        model.addAttribute("userName", UserUtil.getCurrentLoginInfo().getUserName());
        return "errorPage";
    }

    /**
     * 处理前端传来的参数不在约束内的异常 (validation抛出的异常)
     * @param e ConstraintViolationException
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public JsonResult getErrorArgsInfo(ConstraintViolationException e){
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> collect = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        String delimitedString = StringUtils.collectionToDelimitedString(collect, "。", "【", "】");
        return JsonResult.error(delimitedString);
    }
}
