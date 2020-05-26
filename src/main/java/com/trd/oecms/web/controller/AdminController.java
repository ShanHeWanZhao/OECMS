package com.trd.oecms.web.controller;

import com.trd.oecms.annotation.RequireAdmin;
import com.trd.oecms.model.LoginInfo;
import com.trd.oecms.service.ILoginInfoService;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author tanruidong
 * @date 2020-04-15 11:40
 */
@RestController
@RequestMapping("/admin")
@Slf4j
@Validated
public class AdminController {

    private final ILoginInfoService loginInfoService;

    public AdminController(ILoginInfoService loginInfoService) {
        this.loginInfoService = loginInfoService;
    }

    /**
	 * 管理员查看用户信息页面导航
	 * @return
	 */
	@RequireAdmin
    @GetMapping("getAllExcludeAdmin")
    public ModelAndView toListPage() {
        return UserUtil.getMv("admin/listAllExcludeAdmin");
    }

    @RequireAdmin
    @GetMapping("/list")
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
    public JsonResult updateLoginInfo(Integer userId,
                                      @NotBlank(message = "{userName.blank}") String userName,
                                      Byte userStatus) {
        try{
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUserId(userId).setUserStatus(userStatus).setUserName(userName.trim());
            loginInfoService.updateSelectiveById(loginInfo);
            return JsonResult.ok("更新成功");
        }catch(Exception e){
            log.error("更新id为【{}】用户的登录信息失败，更新人：{}", userId, UserUtil.getCurrentLoginInfo().getUserName());
            return JsonResult.error(e.getMessage());
        }
    }

}
