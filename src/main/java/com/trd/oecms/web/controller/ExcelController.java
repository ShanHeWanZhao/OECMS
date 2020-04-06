package com.trd.oecms.web.controller;

import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.POIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Trd
 * @date 2020-04-05 19:53
 */
@Controller
@RequestMapping("excel")
public class ExcelController {

	private Logger logger = LoggerFactory.getLogger(ExcelController.class);

	@RequestMapping("upload")
	@ResponseBody
	public JsonResult upload(MultipartFile file){
		try {
			List<String[]> strings = POIUtil.readExcel(file);
			for(String[] str: strings){
				System.out.println(Arrays.toString(str));
			}
			return JsonResult.ok(strings);
		} catch (IOException e) {
			logger.error("读取Excel失败，原因：{}",e.getMessage(),e);
			return JsonResult.error(e.getMessage());
		}
	}

	@RequestMapping("index")
	public String toIndex(){
		return "excelUpload";
	}
}
