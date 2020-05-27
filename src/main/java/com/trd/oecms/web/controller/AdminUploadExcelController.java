package com.trd.oecms.web.controller;

import com.trd.oecms.annotation.RequireAdmin;
import com.trd.oecms.constants.enums.UserStatusEnum;
import com.trd.oecms.constants.enums.UserTypeEnum;
import com.trd.oecms.model.ExpCourse;
import com.trd.oecms.model.LoginInfo;
import com.trd.oecms.service.IExpCourseService;
import com.trd.oecms.service.ILoginInfoService;
import com.trd.oecms.service.IStudentClassService;
import com.trd.oecms.utils.DateUtils;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.POIUtil;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Trd
 * @date 2020-04-05 19:53
 */
@RestController
@RequestMapping("excel")
@Slf4j
public class AdminUploadExcelController {

	private final IStudentClassService studentClassService;
	private final ILoginInfoService loginInfoService;
	private final IExpCourseService expCourseService;

	public AdminUploadExcelController(IStudentClassService studentClassService,
									  ILoginInfoService loginInfoService,
									  IExpCourseService expCourseService) {
		this.studentClassService = studentClassService;
		this.loginInfoService = loginInfoService;
		this.expCourseService = expCourseService;
	}

	@RequireAdmin
	@RequestMapping("prepareUploadLoginInfo")
	public ModelAndView prepareUploadLoginInfo(){
		return UserUtil.getMv("admin/uploadLoginInfo");
	}

	@RequireAdmin
	@RequestMapping("prepareUploadExpCourse")
	public ModelAndView prepareUploadExpCourse(){
		return UserUtil.getMv("admin/uploadExpCourse");
	}

	/**
	 * 上传登录信息excel
	 * @param file
	 * @return
	 */
	@RequireAdmin
	@RequestMapping("uploadLoginInfo")
	public JsonResult uploadLoginInfo(MultipartFile file){
		try {
			List<LoginInfo> loginInfoList = toLoginInfoList(POIUtil.readExcel(file));
			int successCount = loginInfoService.insertBatch(loginInfoList);
			return JsonResult.ok("登录信息保存成功，成功插入【"+successCount+"】条数据");
		} catch (IllegalArgumentException e1){
			log.error("参数给定错误，原因：{}", e1.getMessage(), e1);
			return JsonResult.error(e1.getMessage());
		}catch (Exception e) {
			log.error("读取Excel失败，原因：{}",e.getMessage(),e);
			return JsonResult.error(e.getMessage());
		}
	}

	/**
	 * 上传实验课程excel
	 * @param file
	 * @return
	 */
	@RequireAdmin
	@RequestMapping("uploadExpCourse")
	public JsonResult uploadExpCourse(MultipartFile file){
		try {
			// excel转为集合对象
			List<ExpCourse> expCourseList = toExpCourseList(POIUtil.readExcel(file));
			return expCourseService.batchInsertData(expCourseList);
		} catch (IllegalArgumentException e1){
			log.error("参数给定错误，原因：{}", e1.getMessage(), e1);
			return JsonResult.error(e1.getMessage());
		}catch (Exception e) {
			log.error("读取Excel失败，原因：{}",e.getMessage(),e);
			return JsonResult.error(e.getMessage());
		}
	}

	/**
	 * excel中实验课程信息批量转为具体的java对象
	 * @param readExcel
	 * @return
	 */
	private List<ExpCourse> toExpCourseList(List<String[]> readExcel) {
		ArrayList<ExpCourse> expCourseList = new ArrayList<>();
		readExcel.forEach(excelInfo ->{
			ExpCourse expCourse = new ExpCourse(excelInfo[0], excelInfo[1], new Byte("0"), new Date());
			Integer classId = studentClassService.getIdByClassName(excelInfo[4]);
			Assert.notNull(classId, "实验名为【"+expCourse.getExpCourseName()+"】的班级信息不正确，没有【"+excelInfo[4]+"】这个班");
			Integer teacherId = loginInfoService.getIdByTeacherName(excelInfo[3]);
			Assert.notNull(teacherId, "实验名为【"+expCourse.getExpCourseName()+"】的任课教师信息不正确，没有【"+excelInfo[3]+"】这个教师");
			expCourse.setStudentClassId(classId);
			expCourse.setExpCourseTime(DateUtils.parseStringDate(excelInfo[2]));
			expCourse.setTeacherId(teacherId);
			expCourseList.add(expCourse);
		});
		return expCourseList;
	}

	/**
	 * excel中登录用户信息批量转为具体的java对象
	 * @param excelInfoList
	 * @return
	 */
	private List<LoginInfo> toLoginInfoList(List<String[]> excelInfoList){
		ArrayList<LoginInfo> loginInfoList = new ArrayList<>();
		for (String[] excelInfo: excelInfoList){
			// 取出excel单元格信息
			String username = excelInfo[0];
			String accountNumber = excelInfo[1];
			String password = excelInfo[2];
			String userType = excelInfo[3];
			String studentClassName = excelInfo[4];
			// 密码和账号格式校验
			Assert.isTrue(password.matches("^[A-Za-z0-9]+$"), "姓名为【"+username+"】的密码【"+password+"】格式不匹配，只能由数字和字母组成");
			Assert.isTrue(accountNumber.matches("^[A-Za-z0-9]+$"), "姓名为【"+username+"】的账号【"+accountNumber+"】格式不匹配，只能由数字和字母组成");
			// 重复账号校验
			int count = loginInfoService.getCountByAccountNumber(accountNumber);
			Assert.isTrue(count == 0, "姓名为【"+username+"】的账号已经存在，请勿重复创建！！" );
			LoginInfo loginInfo = new LoginInfo(username, accountNumber, password, new Date(), UserStatusEnum.ENABLE.state(), -1);
			// 学生设置班级
			UserTypeEnum type = UserTypeEnum.getByUserTypeName(userType);
			if (type == UserTypeEnum.STUDENT){
				Integer classId = studentClassService.getIdByClassName(studentClassName);
				Assert.notNull(classId, "学生姓名为【"+loginInfo.getUserName()+"】的班级信息不正确，没有【"+studentClassName+"】这个班");
				loginInfo.setUserClassId(classId);
			}
			loginInfo.setUserType((byte) type.ordinal());
			loginInfoList.add(loginInfo);
		}
		return loginInfoList;
	}

}
