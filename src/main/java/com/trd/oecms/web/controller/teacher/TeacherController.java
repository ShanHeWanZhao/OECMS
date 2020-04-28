package com.trd.oecms.web.controller.teacher;

import com.trd.oecms.annotation.RequireTeacher;
import com.trd.oecms.model.CourseTask;
import com.trd.oecms.model.ExpCourse;
import com.trd.oecms.query.CourseTaskQueryConditionsByTeacher;
import com.trd.oecms.query.ExpCourseQueryConditions;
import com.trd.oecms.service.ICourseTaskService;
import com.trd.oecms.service.IExpCourseService;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Size;
import java.io.FileInputStream;

/**
 * @author tanruidong
 * @date 2020-04-16 15:25
 */
@Controller
@RequestMapping("teacher")
@Slf4j
@Validated
public class TeacherController {

    @Autowired
    private IExpCourseService expCourseService;
    @Autowired
	private ICourseTaskService courseTaskService;

    // 讲义存放路径
    @Value("${experimentalCourse.resources.word.materials}")
    private String teachMaterialsPath;
    // 实验结果存放路径
    @Value("${experimentalCourse.resources.word.result_data}")
    private String resultDataPath;

    /**
     * 导航到查看实验课页面
     * @param model
     * @return
     */
    @RequireTeacher
    @GetMapping("getMyExpCourse")
    public String toListPage(Model model) {
        UserUtil.addLoginInfo(model);
        return "/teacher/listMyExpCourse";
    }

    /**
     * 导航到查看学生完成情况页面
     * @param model
     * @return
     */
    @RequireTeacher
    @RequestMapping("handleCourseTask")
    public String handleCourseTask(Model model){
        UserUtil.addLoginInfo(model);
        return "/teacher/listStudentCourseTask";
    }
    /**
     * 实验课程按条件分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequireTeacher
    @PostMapping("/list")
    @ResponseBody
    public JsonResult listExpCourse(@RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize, ExpCourseQueryConditions conditions) {
        try{
            int offset = (pageNum - 1) * pageSize;
            return expCourseService.listExpCourse(offset, pageSize, conditions);
        }catch(Exception e){
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }
    /**
     * 课程任务按条件分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequireTeacher
    @PostMapping("/listCourseTask")
    @ResponseBody
    public JsonResult listCourseTask(@RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize, CourseTaskQueryConditionsByTeacher conditions) {
        try{
            int offset = (pageNum - 1) * pageSize;
            return courseTaskService.listCourseTaskByTeacher(offset, pageSize, conditions);
        }catch(Exception e){
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    /**
     * 教师上传实验讲义，一个实验最多能上传三次
     * @param multipartFile
     * @return
     */
    @RequireTeacher
    @RequestMapping("uploadTeachMaterials")
    @ResponseBody
    public JsonResult uploadTeachMaterials( MultipartFile multipartFile, Integer expCourseId){
        try{
            long processTime = expCourseService.uploadTeachMaterials(multipartFile, expCourseId);
            return JsonResult.ok("上传成功！！处理时间为：【"+processTime+"秒】");
        } catch(Exception e){
            log.error("教师上传实验讲义失败。原因：【{}】，上传人：【{}】，文件名【{}】",e.getMessage(), UserUtil.getCurrentLoginInfo().getUserName(), multipartFile.getOriginalFilename());
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    /**
     * 查看实验讲义
     * @param expCourseMaterial
     * @param response
     */
    @GetMapping("show")
    public void show(String expCourseMaterial,HttpServletResponse response){
        try(FileInputStream fileInputStream =
                    new FileInputStream(teachMaterialsPath + expCourseMaterial);
            ServletOutputStream outputStream = response.getOutputStream()
        ){
            IOUtils.copy(fileInputStream, outputStream);
        } catch(Exception e){
            log.error("pdf读取出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 更新实验课程信息
     * @param expCourse
     * @param expCourseDescription
     * @return
     */
    @RequireTeacher
    @PostMapping("updateExpCourse")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult updateExpCourse(ExpCourse expCourse,
                                      @Size(max = 510, message = "{expCourseDescription.max.length}") String expCourseDescription){
        try{
            ExpCourse newExpCourse = new ExpCourse();
            newExpCourse.setExpCourseId(expCourse.getExpCourseId()).
                    setExpCourseTime(expCourse.getExpCourseTime()).
                    setExpCourseDescription(expCourseDescription).
                    setExpCourseLocation(expCourse.getExpCourseLocation()).
                    setExpCourseName(expCourse.getExpCourseName()).
                    setExpCourseStatus(expCourse.getExpCourseStatus());
            Integer expCourseId = newExpCourse.getExpCourseId();
            expCourseService.updateSelectiveById(newExpCourse, false);
            // 根据实验课程状态更新对应的课程任务状态
            int count;
            switch (newExpCourse.getExpCourseStatus().intValue()){
                // 进行中
                case 1:
                    count = courseTaskService.updateCourseTaskStatusByExpCourseStatus(expCourseId, new Byte("1"));
                    break;
                // 取消和结束
                case 2 :
                case 3 :
                    count = courseTaskService.updateCourseTaskStatusByExpCourseStatus(expCourseId, new Byte("3"));
                    break;
                default:
                    throw new IllegalStateException("人间正道是沧桑，小伙子！: " + newExpCourse.getExpCourseStatus().intValue());
            }
            return JsonResult.ok("更新成功，修改了【"+count+"】条对应的课程任务");
        } catch(Exception e){
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    /**
     * 教师对学生提交的结果进行打分
     * @param courseTask
     * @return
     */
    @RequireTeacher
    @PostMapping("submitResult")
    @ResponseBody
    public JsonResult submitResult(CourseTask courseTask){
        try{
            CourseTask newCourseTask = new CourseTask();
            newCourseTask.setCourseTaskId(courseTask.getCourseTaskId()).
                    setCourseTaskComment(courseTask.getCourseTaskComment())
                    .setExpCourseGrade(courseTask.getExpCourseGrade());
            courseTaskService.updateSelectiveById(newCourseTask, true, false);
            return JsonResult.ok("打分成功");
        } catch(Exception e){
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }
}
