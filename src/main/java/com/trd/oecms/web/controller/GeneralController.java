package com.trd.oecms.web.controller;

import com.trd.oecms.annotation.EqualsCurrentUser;
import com.trd.oecms.constants.enums.LoginInfoTypeEnum;
import com.trd.oecms.constants.enums.UserTypeEnum;
import com.trd.oecms.model.LoginInfo;
import com.trd.oecms.service.ILoginInfoService;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Pattern;

/**
 * 通用控制器，包括：登录位置定位，登录，登出，改密，等
 *
 * @author tanruidong
 * @date 2020-03-31 16:58
 */
@Controller
@Validated
@Slf4j
public class GeneralController {

    private final ILoginInfoService loginInfoService;

    public GeneralController(ILoginInfoService loginInfoService) {
        this.loginInfoService = loginInfoService;
    }

    /**
     * 登录页面定位索引
     *
     * @return
     */
    @GetMapping("index")
    public String index() {
        return "login";
    }

    /**
     * 用户登录
     *
     * @param accountNum 账号
     * @param password   密码
     * @param type       类型
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public JsonResult login(@NotBlank(message = "{accountNum.blank}") String accountNum,
                            @NotBlank(message = "{password.blank}") String password,
                            Byte type) {
        try {
            UserTypeEnum userType = UserTypeEnum.getByNumber(type);
            LoginInfo info = loginInfoService.getUser(accountNum, password, userType);
            UserUtil.getSession().invalidate();
            // 将当前用户信息保存到session中
            UserUtil.setCurrentLoginInfo(info);
            log.info("登录成功，登录账号：{}，登陆者：{}", info.getAccountNumber(), info.getUserName());
            return JsonResult.ok("/success");
        } catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
    }

    /**
     * 登陆成功，页面跳转
     *
     * @return
     */
    @GetMapping("/success")
    public ModelAndView toSuccess() {
        return UserUtil.getMv("success");
    }

    /**
     * 登出系统
     *
     * @return
     */
    @GetMapping("logout")
    public String logout() {
        UserUtil.getSession().invalidate();
        return "redirect:/index";
    }

    /**
     * 修改密码
     *
     * @param userId      用户id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PostMapping("/modifyPassword")
    @ResponseBody
    public JsonResult updatePassword(@EqualsCurrentUser
                                             Integer userId,
                                     @EqualsCurrentUser(infoType = LoginInfoTypeEnum.PASSWORD, message = "{user.oldPassword}")
                                             String oldPassword,
                                     @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$", message = "{newPassword.format.notMatch}")
                                     @EqualsCurrentUser(infoType = LoginInfoTypeEnum.PASSWORD,
                                             requireSamePassword = false,
                                             message = "{require.newPassword.notEquals.oldPassword}")
                                             String newPassword) {
        try {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUserId(userId).setPassword(newPassword);
            loginInfoService.updateSelectiveById(loginInfo);
            return JsonResult.ok("/logout");
        } catch (Exception e) {
            log.error("用户id为【{}】的密码修改失败", userId, e);
            return JsonResult.error(e.getMessage());
        }
    }
}
