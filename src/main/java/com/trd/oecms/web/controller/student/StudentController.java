package com.trd.oecms.web.controller.student;

import com.trd.oecms.annotation.RequireStudent;
import com.trd.oecms.annotation.RequireTeacher;
import com.trd.oecms.query.CourseTaskQueryConditionsByStudent;
import com.trd.oecms.service.ICourseTaskService;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;

/**
 * @author tanruidong
 * @date 2020-04-20 11:34
 */
@Controller
@RequestMapping("student")
@Slf4j
public class StudentController {

    @Autowired
    private ICourseTaskService courseTaskService;

    // 实验结果存放路径
    @Value("${experimentalCourse.resources.word.result_data}")
    private String resultDataPath;
    /**
     * 导航到查看课程任务情况页面
     * @param model
     * @return
     */
    @RequireStudent
    @GetMapping("getMyCourseTask")
    public String toListPage(Model model) {
        UserUtil.addLoginInfo(model);
        return "/student/listMyCourseTask";
    }

    /**
     * 课程任务按条件分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequireStudent
    @PostMapping("/listCourseTask")
    @ResponseBody
    public JsonResult listCourseTask(@RequestParam("pageNum") Integer pageNum,
                                     @RequestParam("pageSize") Integer pageSize, CourseTaskQueryConditionsByStudent conditions) {
        try{
            int offset = (pageNum - 1) * pageSize;
            return courseTaskService.listCourseTaskByStudent(offset, pageSize, conditions);
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
    @RequireStudent
    @RequestMapping("uploadResultData")
    @ResponseBody
    public JsonResult uploadResultData(MultipartFile multipartFile, Integer courseTaskId){
        try{
            long processTime = courseTaskService.uploadResultData(multipartFile, courseTaskId);
            return JsonResult.ok("上传成功！！处理时间为：【"+processTime+"秒】");
        } catch(Exception e){
            log.error("学生上传实验讲义失败。原因：【{}】，上传人：【{}】，文件名【{}】",e.getMessage(), UserUtil.getCurrentLoginInfo().getUserName(), multipartFile.getOriginalFilename());
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    /**
     * 查看实验讲义
     * @param expCourseResultData
     * @param response
     */
    @GetMapping("show")
    public void show(String expCourseResultData, HttpServletResponse response){
        try(FileInputStream fileInputStream =
                    new FileInputStream(resultDataPath + expCourseResultData);
            ServletOutputStream outputStream = response.getOutputStream()
        ){
            IOUtils.copy(fileInputStream, outputStream);
        } catch(Exception e){
            log.error("pdf读取出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }
}
