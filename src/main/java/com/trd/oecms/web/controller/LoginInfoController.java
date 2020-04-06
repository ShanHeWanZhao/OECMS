package com.trd.oecms.web.controller;

import com.trd.oecms.service.ILoginInfoService;
import com.trd.oecms.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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

	@PostMapping("/login")
	@ResponseBody
	public JsonResult login(String username, String password, Integer type) {
		if (!StringUtils.hasText(username) || !StringUtils.hasText(password)){
			return JsonResult.error("账号和密码都不能为空！");
		}
		return JsonResult.ok();
//		try{
//		    loginInfoService.getUser(username, password, type);
//		}catch(Exception e){
//
//		}
	}
}
