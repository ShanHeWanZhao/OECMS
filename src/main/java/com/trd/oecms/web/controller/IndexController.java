package com.trd.oecms.web.controller;

import com.trd.oecms.utils.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

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

//    @GetMapping("errorPage")
//    public String toErrorPage(Model model){
//        model.addAttribute("loginInfo", UserUtil.getCurrentLoginInfo());
//        return "errorPage";
//    }

    /**
     * 登出系统
     * @return
     */
    @GetMapping("logout")
    public String logout(){
        HttpSession session = UserUtil.getSession();
        session.invalidate();
        return "redirect:/index";
    }
}
