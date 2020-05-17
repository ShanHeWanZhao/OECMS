package com.trd.oecms.service.impl;

import com.trd.oecms.dao.CourseTaskMapper;
import com.trd.oecms.model.CourseTask;
import com.trd.oecms.query.CourseTaskQueryConditionsByStudent;
import com.trd.oecms.query.CourseTaskQueryConditionsByTeacher;
import com.trd.oecms.query.StudentPaginationCourseTask;
import com.trd.oecms.query.TeacherPaginationCourseTask;
import com.trd.oecms.service.ICourseTaskService;
import com.trd.oecms.utils.DateUtils;
import com.trd.oecms.utils.FileUtils;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author tanruidong
 * @date 2020-04-17 17:14
 */
@Service
@Slf4j
public class CourseTaskServiceImpl implements ICourseTaskService {

    @Autowired
    private CourseTaskMapper courseTaskMapper;

    // 实验结果存放路径
    @Value("${experimentalCourse.resources.word.result_data}")
    private String resultDataPath;

    @Override
    public int insert(CourseTask record) {
        return courseTaskMapper.insert(record);
    }

    @Override
    public CourseTask selectByPrimaryKey(Integer courseTaskId) {
        return courseTaskMapper.selectByPrimaryKey(courseTaskId);
    }

    @Override
    public List<CourseTask> selectAll() {
        return courseTaskMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(CourseTask record) {
        return courseTaskMapper.updateByPrimaryKey(record);
    }

    @Override
    public int insertBatch(List<CourseTask> courseTaskList) {
        return courseTaskMapper.insertBatch(courseTaskList);
    }

	@Override
	public JsonResult listCourseTaskByTeacher(int offset, Integer pageSize, CourseTaskQueryConditionsByTeacher conditions) {
		try{
			List<TeacherPaginationCourseTask> taskList = courseTaskMapper.listCourseTaskByTeacher(offset, pageSize, conditions);
			int count = courseTaskMapper.listCourseTaskCountByTeacher(offset, pageSize, conditions);
			JsonResult jsonResult = JsonResult.ok();
			if (taskList != null && taskList.size() > 0){
				jsonResult.setData(taskList);
				jsonResult.setCount(count);
			}
			return jsonResult;
		}catch(Exception e){
			e.printStackTrace();
			return JsonResult.error(e.getMessage());
		}
	}

    @Override
    public void updateSelectiveById(CourseTask newCourseTask, Boolean isTaskEnd, Boolean isNeedAddResultDataCount) {
        courseTaskMapper.updateSelectiveById(newCourseTask, isTaskEnd, isNeedAddResultDataCount);
    }

    @Override
    public JsonResult listCourseTaskByStudent(int offset, Integer pageSize, CourseTaskQueryConditionsByStudent conditions) {
        try{
            List<StudentPaginationCourseTask> taskList = courseTaskMapper.listCourseTaskByStudent(offset, pageSize, conditions);
            int count = courseTaskMapper.listCourseTaskCountByStudent(offset, pageSize, conditions);
            JsonResult jsonResult = JsonResult.ok();
            if (taskList != null && taskList.size() > 0){
                jsonResult.setData(taskList);
                jsonResult.setCount(count);
            }
            return jsonResult;
        }catch(Exception e){
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    @Override
    public long uploadResultData(MultipartFile multipartFile, Integer courseTaskId) throws Exception {
        long handleStartTime = System.currentTimeMillis();
        String pdfName = FileUtils.saveFile(multipartFile, resultDataPath);
        // 构建更新器，只更新讲义存放路径（即讲义名）
        CourseTask newCourseTask = new CourseTask().
                setCourseTaskId(courseTaskId).
                setCourseTaskStatus(new Byte("2")).
                setExpCourseResultData(pdfName);
        this.updateSelectiveById(newCourseTask, false, true);
        long secondsDuration = DateUtils.getSecondsDuration(handleStartTime, System.currentTimeMillis());
        log.info("文件上传成功，上传人为【{}】，文件名为【{}】，处理时间为【{}】秒", UserUtil.getCurrentLoginInfo().getUserName(), multipartFile.getOriginalFilename(), secondsDuration);
        return secondsDuration;
    }

    @Override
    public int updateCourseTaskStatusByExpCourseStatus(Integer expCourseId, Byte targetCourseTaskStatus) {
        return courseTaskMapper.updateCourseTaskStatusByExpCourseStatus(expCourseId, targetCourseTaskStatus);
    }

}
