package com.trd.oecms.web.controller;

import com.trd.oecms.entities.LoginInfo;
import com.trd.oecms.entities.enums.UserTypeEnum;
import com.trd.oecms.service.ILoginInfoService;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Trd
 * @date 2020-04-06 20:37
 */
@Controller
@RequestMapping("/loginInfo")
@Slf4j
@Validated
public class LoginInfoController {

	@Autowired
	private ILoginInfoService loginInfoService;

	/**
	 * 用户登录
	 * @param accountNum 账号
	 * @param password 密码
	 * @param type 类型
	 * @return
	 */
	@PostMapping("/login")
	@ResponseBody
	public JsonResult login(@NotBlank(message = "{accountNum.blank}") String accountNum,
							@NotBlank(message = "{password.blank}") String password,
							Byte type) {
		try{
			UserTypeEnum userType = UserTypeEnum.getByNumber(type);
			LoginInfo info = loginInfoService.getUser(accountNum, password, userType);
			// 将当前用户信息保存到session中
			UserUtil.setCurrentLoginInfo(info);
			log.info("登录成功，登录账号：{}，登陆者：{}",info.getAccountNumber(), info.getUserName());
			return JsonResult.ok("/loginInfo/success");
		}catch(Exception e){
			return JsonResult.error(e.getMessage());
		}
	}

	/**
	 * 登陆成功，页面跳转
	 * @param model
	 * @return
	 */
	@GetMapping("/success")
	public String toSuccess(Model model){
		UserUtil.addMenuInfo(model);
		return "success";
	}

}

