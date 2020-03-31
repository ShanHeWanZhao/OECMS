package com.trd.oecms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

}
