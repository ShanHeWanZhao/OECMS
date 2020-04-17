package com.trd.oecms.web.controller.admin;

import com.trd.oecms.annotation.RequireAdmin;
import com.trd.oecms.model.CourseTask;
import com.trd.oecms.model.ExpCourse;
import com.trd.oecms.model.LoginInfo;
import com.trd.oecms.constants.enums.UserTypeEnum;
import com.trd.oecms.service.ICourseTaskService;
import com.trd.oecms.service.IExpCourseService;
import com.trd.oecms.service.ILoginInfoService;
import com.trd.oecms.service.IStudentClassService;
import com.trd.oecms.utils.DateUtils;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.POIUtil;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Trd
 * @date 2020-04-05 19:53
 */
@Controller
@RequestMapping("excel")
@Slf4j
public class ExcelController {

	@Autowired
	private IStudentClassService studentClassService;
	@Autowired
	private ILoginInfoService loginInfoService;
	@Autowired
	private IExpCourseService expCourseService;
	@Autowired
	private ICourseTaskService courseTaskService;

	@RequireAdmin
	@RequestMapping("prepareUploadLoginInfo")
	public String prepareUploadLoginInfo(Model model){
		UserUtil.addLoginInfo(model);
		return "admin/uploadLoginInfo";
	}

	@RequireAdmin
	@RequestMapping("prepareUploadExpCourse")
	public String prepareUploadExpCourse(Model model){
		UserUtil.addLoginInfo(model);
		return "admin/uploadExpCourse";
	}

	/**
	 * 上传登录信息excel
	 * @param file
	 * @return
	 */
	@RequireAdmin
	@RequestMapping("uploadLoginInfo")
	@ResponseBody
	public JsonResult uploadLoginInfo(MultipartFile file){
		try {
			List<LoginInfo> loginInfoList = toLoginInfoList(POIUtil.readExcel(file));
			loginInfoList.forEach(System.out::println);
			int successCount = loginInfoService.insertBatch(loginInfoList);
			return JsonResult.ok("登录信息保存成功，成功插入【"+successCount+"】条数据");
		} catch (IllegalArgumentException e1){
			log.error("参数给定错误，原因：{}", e1.getMessage());
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
	@ResponseBody
	public JsonResult uploadExpCourse(MultipartFile file){
		try {
			// excel转为集合对象
			List<ExpCourse> expCourseList = toExpCourseList(POIUtil.readExcel(file));
			// 批量插入ExpCourse
			int successCount = expCourseService.insertBatch(expCourseList);
			// 准备CourseTask
			List<CourseTask> courseTaskList = new ArrayList<>();
			// 为对应班级的学生构建CourseTask
			expCourseList.forEach(expCourse -> {
				Integer studentClassId = expCourse.getStudentClassId();
				List<Integer> studentIdList = loginInfoService.getStudentIdByClassId(studentClassId);
				studentIdList.forEach(studentId -> {
					CourseTask courseTask = new CourseTask();
					courseTask.setExpCourseId(expCourse.getExpCourseId()).
							setTeacherId(expCourse.getTeacherId()).
							setStudentId(studentId).
							setCourseTaskCreateTime(new Date()).
							setCourseTaskStatus(new Byte("0"));
					courseTaskList.add(courseTask);
				});
			});
			// 批量插入CourseTask
			int courseTaskCreateSuccessCount = courseTaskService.insertBatch(courseTaskList);
			return JsonResult.ok("实验课程保存成功，成功插入【"+successCount+"】条实验课程数据。" +
					"并为对应的班级学生共建立了【"+courseTaskCreateSuccessCount+"】条实验课程任务数据");
		} catch (IllegalArgumentException e1){
			log.error("参数给定错误，原因：{}", e1.getMessage());
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
		excelInfoList.forEach(excelInfo -> {
			format(excelInfo[0], excelInfo[1], excelInfo[2]);
			LoginInfo loginInfo = new LoginInfo(excelInfo[0], excelInfo[1], excelInfo[2], new Date(), new Byte("0"), -1);
			UserTypeEnum type = UserTypeEnum.getByUserTypeName(excelInfo[3]);
			// 只有学生才会存在班级
			if (type == UserTypeEnum.STUDENT){
				Integer classId = studentClassService.getIdByClassName(excelInfo[4]);
				Assert.notNull(classId, "学生姓名为【"+loginInfo.getUserName()+"】的班级信息不正确，没有【"+excelInfo[4]+"】这个班");
				loginInfo.setUserClassId(classId);
			}
			loginInfo.setUserType((byte) type.ordinal());
			loginInfoList.add(loginInfo);
		});
		return loginInfoList;
	}

	private void format(String userName, String needMatch1, String needMatch2){
		Assert.isTrue(needMatch2.matches("^[A-Za-z0-9]+$"), "名字叫【"+userName+"】的【"+needMatch2+"】格式不匹配，只能由数字和字母组成");
		Assert.isTrue(needMatch1.matches("^[A-Za-z0-9]+$"), "名字叫【"+userName+"】的【"+needMatch1+"】格式不匹配，只能由数字和字母组成");
	}
}
