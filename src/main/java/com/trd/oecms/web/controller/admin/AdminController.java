package com.trd.oecms.web.controller.admin;

import com.trd.oecms.annotation.RequireAdmin;
import com.trd.oecms.model.LoginInfo;
import com.trd.oecms.service.ILoginInfoService;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author tanruidong
 * @date 2020-04-15 11:40
 */
@Controller
@RequestMapping("/admin")
@Slf4j
@Validated
public class AdminController {

    @Autowired
    private ILoginInfoService loginInfoService;

    @RequireAdmin
    @GetMapping("getAllExcludeAdmin")
    public String toListPage(Model model) {
        UserUtil.addLoginInfo(model);
        return "admin/listAllExcludeAdmin";
    }

    @RequireAdmin
    @GetMapping("/list")
    @ResponseBody
    public JsonResult getAllExcludeAdmin(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize,
                                         LoginInfo loginInfo) {
        try{
            int offset = (pageNum - 1) * pageSize;
            return loginInfoService.listExcludeAdmin(offset, pageSize, loginInfo);
        }catch(Exception e){
            return JsonResult.error(e.getMessage());
        }
    }
    @RequireAdmin
    @PostMapping("/updateLoginInfo")
    @ResponseBody
    public JsonResult updateLoginInfo(Integer userId, @NotBlank(message = "{userName.blank}") String userName, Byte userStatus) {
        try{
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUserId(userId).setUserStatus(userStatus).setUserName(userName.trim());
            loginInfoService.updateSelectiveById(loginInfo);
            return JsonResult.ok("更新成功");
        }catch(Exception e){
            return JsonResult.error(e.getMessage());
        }
    }

}
