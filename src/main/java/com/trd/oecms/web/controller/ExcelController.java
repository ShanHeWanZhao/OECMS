package com.trd.oecms.web.controller;

import com.trd.oecms.annotation.RequireAdmin;
import com.trd.oecms.entities.LoginInfo;
import com.trd.oecms.entities.enums.UserTypeEnum;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.POIUtil;
import com.trd.oecms.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Trd
 * @date 2020-04-05 19:53
 */
@Controller
@RequestMapping("excel")
public class ExcelController {

	private Logger logger = LoggerFactory.getLogger(ExcelController.class);

	@RequireAdmin
	@RequestMapping("upload")
	@ResponseBody
	public JsonResult upload(MultipartFile file){
		try {
			List<LoginInfo> loginInfoList = toLoginInfo(POIUtil.readExcel(file));
			loginInfoList.forEach(System.out::println);
			return JsonResult.ok(loginInfoList);
		} catch (IOException e) {
			logger.error("读取Excel失败，原因：{}",e.getMessage(),e);
			return JsonResult.error(e.getMessage());
		} catch (IllegalArgumentException e1){
			logger.error("参数给定错误，原因：{}", e1.getMessage());
			return JsonResult.error(e1.getMessage());
		}
	}

	@RequireAdmin
	@RequestMapping("index")
	public String toIndex(Model model){
		UserUtil.addMenuInfo(model);
		return "admin/excelUpload";
	}

	/**
	 * excel中登录用户信息批量转为具体的java对象
	 * @param excelInfoList
	 * @return
	 */
	private List<LoginInfo> toLoginInfo(List<String[]> excelInfoList){
		ArrayList<LoginInfo> loginInfoList = new ArrayList<>();
		LoginInfo loginInfo;
		for (String[] excelInfo : excelInfoList) {
			loginInfo = new LoginInfo(excelInfo[0], excelInfo[1], excelInfo[2], new Date(), new Byte("0"));
			UserTypeEnum type = UserTypeEnum.getByUserTypeName(excelInfo[3]);
			loginInfo.setUserType((byte) type.ordinal());
			loginInfoList.add(loginInfo);
		}
		return loginInfoList;
	}
}
