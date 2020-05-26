package com.trd.oecms.service.impl;

import com.trd.oecms.dao.ExpCourseMapper;
import com.trd.oecms.model.CourseTask;
import com.trd.oecms.model.ExpCourse;
import com.trd.oecms.query.ExpCourseQueryConditions;
import com.trd.oecms.service.ICourseTaskService;
import com.trd.oecms.service.IExpCourseService;
import com.trd.oecms.service.ILoginInfoService;
import com.trd.oecms.utils.DateUtils;
import com.trd.oecms.utils.FileUtils;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tanruidong
 * @date 2020-04-14 11:40
 */
@Service
@Slf4j
public class ExpCourseServiceImpl implements IExpCourseService {

    private final ExpCourseMapper expCourseMapper;
    private final ICourseTaskService courseTaskService;
    private final ILoginInfoService loginInfoService;

    public ExpCourseServiceImpl(ExpCourseMapper expCourseMapper,
                                ICourseTaskService courseTaskService,
                                ILoginInfoService loginInfoService) {
        this.expCourseMapper = expCourseMapper;
        this.courseTaskService = courseTaskService;
        this.loginInfoService = loginInfoService;
    }

    // 讲义存放路径
    @Value("${experimentalCourse.resources.word.materials}")
    private String teachMaterialsPath;

    @Override
    public int insert(ExpCourse expCourse) {
        return expCourseMapper.insert(expCourse);
    }

    @Override
    public ExpCourse selectByPrimaryKey(Integer expCourseId) {
        return expCourseMapper.selectByPrimaryKey(expCourseId);
    }

    @Override
    public List<ExpCourse> selectAll() {
        return expCourseMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(ExpCourse expCourse) {
        return expCourseMapper.updateByPrimaryKey(expCourse);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult batchInsertData(List<ExpCourse> expCourseList) {
        int successCount = expCourseMapper.insertBatch(expCourseList);
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
    }

    @Override
    public JsonResult listExpCourse(int offset, Integer pageSize, ExpCourseQueryConditions conditions) {
        try{
            List<ExpCourse> expCourseList = expCourseMapper.listExpCourse(offset, pageSize, conditions);
            int count = expCourseMapper.listExpCourseCount(offset, pageSize, conditions);
            JsonResult jsonResult = JsonResult.ok();
            if (expCourseList != null && expCourseList.size() > 0){
                jsonResult.setData(expCourseList);
                jsonResult.setCount(count);
            }
            return jsonResult;
        }catch(Exception e){
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    @Override
    public void updateSelectiveById(ExpCourse expCourse, Boolean needAddUploadCount) {
        expCourseMapper.updateSelectiveById(expCourse, needAddUploadCount);
    }

    @Override
    public long uploadTeachMaterials(MultipartFile multipartFile, Integer expCourseId) throws Exception {
        long handleStartTime = System.currentTimeMillis();
        String pdfName = FileUtils.saveFile(multipartFile, teachMaterialsPath);
        // 构建更新器，只更新讲义存放路径（即讲义名）
        ExpCourse expCourse = new ExpCourse().setExpCourseId(expCourseId).setExpCourseMaterial(pdfName);
        this.updateSelectiveById(expCourse, true);
        long secondsDuration = DateUtils.getSecondsDuration(handleStartTime, System.currentTimeMillis());
        log.info("文件上传成功，上传人为【{}】，文件名为【{}】，处理时间为【{}】秒", UserUtil.getCurrentLoginInfo().getUserName(), multipartFile.getOriginalFilename(), secondsDuration);
        return secondsDuration;
    }


}
