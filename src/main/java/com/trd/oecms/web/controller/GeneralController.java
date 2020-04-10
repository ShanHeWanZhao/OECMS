package com.trd.oecms.web.controller;

import com.trd.oecms.annotation.EqualsCurrentUser;
import com.trd.oecms.entities.enums.UserInfoType;
import com.trd.oecms.service.ILoginInfoService;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 通用控制器，包括：登出，改密，登录位置定位等
 * @author tanruidong
 * @date 2020-03-31 16:58
 */
@Controller
@Validated
@Slf4j
public class GeneralController {

    @Autowired
    private ILoginInfoService loginInfoService;

	/**
	 * 登录页面定位索引
	 * @return
	 */
    @GetMapping("index")
    public String index(){
        return "login";
    }

    /**
     * 登出系统
     * @return
     */
    @GetMapping("logout")
    public String logout(){
        UserUtil.getSession().invalidate();
        return "redirect:/index";
    }

    /**
     * 修改密码
     * @param userId 用户id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PostMapping("/modifyPassword")
    @ResponseBody
    public JsonResult updatePassword(@EqualsCurrentUser
                                     Integer userId,
                                     @EqualsCurrentUser(infoType = UserInfoType.PASSWORD, message = "{user.oldPassword}")
                                     String oldPassword,
                                     @Size(min = 8, max = 30, message = "{newPassword.size}")
                                     @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{newPassword.format.notMatch}")
                                     @EqualsCurrentUser(infoType = UserInfoType.PASSWORD,
                                                         requireSamePassword = false,
                                                         message = "{require.newPassword.notEquals.oldPassword}")
                                     String newPassword){
        try{
            loginInfoService.updatePassword(userId, newPassword);
            return JsonResult.ok("/logout");
        }catch(Exception e){
            log.error("用户id为【{}】的密码修改失败", userId, e);
            return JsonResult.error(e.getMessage());
        }
}
}