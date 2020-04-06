package com.trd.oecms.web.controller;

import com.trd.oecms.utils.JsonResult;
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

    @GetMapping("success")
    public String success() {
        return "success";
    }
}
