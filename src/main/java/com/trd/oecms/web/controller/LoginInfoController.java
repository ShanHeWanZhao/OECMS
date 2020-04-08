package com.trd.oecms.web.controller;

import com.trd.oecms.annotation.RequireStudent;
import com.trd.oecms.entities.LoginInfo;
import com.trd.oecms.entities.enums.UserTypeEnum;
import com.trd.oecms.service.ILoginInfoService;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class LoginInfoController {
	private final Logger logger = LoggerFactory.getLogger(LoginInfoController.class);

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
	public JsonResult login(String accountNum, String password, Byte type) {
		try{
			UserTypeEnum userType = UserTypeEnum.getByNumber(type);
			LoginInfo info = loginInfoService.getUser(accountNum, password, userType);
			// 将当前用户信息保存到session中
			UserUtil.setCurrentLoginInfo(info);
			logger.info("登录成功，登录账号：{}，登陆者：{}",info.getAccountNumber(), info.getUserName());
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
	@RequireStudent
	public String toSuccess(Model model){
		UserUtil.addMenuInfo(model);
		return "success";
	}
}

