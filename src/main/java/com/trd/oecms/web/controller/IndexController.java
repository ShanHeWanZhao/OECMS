package com.trd.oecms.web.controller;

import com.trd.oecms.entities.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author tanruidong
 * @date 2020-03-31 16:58
 */
@Controller
public class IndexController {

    @GetMapping("index")
    public String index(){
        return "login";
    }

    @PostMapping("login")
    @ResponseBody
    public JsonResult login(String username, String password, Integer type) {
        if (!StringUtils.hasText(username)){
            return JsonResult.error("账号不能为空！");
        }
        if (!StringUtils.hasText(password)){
            return JsonResult.error("密码不能为空！");
        }
        if ("2016032030".equals(username) && "123456".equals(password)){
            return JsonResult.ok("success");
        }else{
            return JsonResult.error("账号或密码不正确");
        }
    }
    @GetMapping("success")
    public String success() {
        return "success";
    }
}
