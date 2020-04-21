package com.trd.oecms.service;

import com.trd.oecms.model.CourseTask;
import com.trd.oecms.query.CourseTaskQueryConditionsByStudent;
import com.trd.oecms.query.CourseTaskQueryConditionsByTeacher;
import com.trd.oecms.utils.JsonResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author tanruidong
 * @date 2020-04-17 17:14
 */
public interface ICourseTaskService {
    int insert(CourseTask record);

    CourseTask selectByPrimaryKey(Integer courseTaskId);

    List<CourseTask> selectAll();

    int updateByPrimaryKey(CourseTask record);


    /**
     * 批量插入课程任务
     * @param courseTaskList
     * @return
     */
    int insertBatch(List<CourseTask> courseTaskList);

	/**
	 * 教师分页查询学生课程任务
	 * @param offset
	 * @param pageSize
	 * @param conditions 查询条件
	 * @return
	 */
	JsonResult listCourseTaskByTeacher(int offset, Integer pageSize, CourseTaskQueryConditionsByTeacher conditions);

	/**
	 * 选择性的更新CourseTask
	 * @param newCourseTask 更新条件
	 * @param isTaskEnd 是否将课程任务状态设置为已完成（只有在教师提交打分时才会设置为true）
	 * @param isNeedAddResultDataCount 是否需要增加实验结果文件上传次数（只有在学生上传实验结果时才能设置为true）
	 */
    void updateSelectiveById(CourseTask newCourseTask, Boolean isTaskEnd, Boolean isNeedAddResultDataCount);

	/**
	 * 学生查询自己的课程任务情况
	 * @param offset
	 * @param pageSize
	 * @param conditions
	 * @return
	 */
	JsonResult listCourseTaskByStudent(int offset, Integer pageSize, CourseTaskQueryConditionsByStudent conditions);

	/**
	 * 学生上传实验结果
	 * @param multipartFile
	 * @param courseTaskId
	 * @return 处理时间 （秒）
	 */
    long uploadResultData(MultipartFile multipartFile, Integer courseTaskId) throws Exception;

	/**
	 * 根据实验课程状态更新对应的课程任务状态
	 * @param expCourseId 实验课id
	 * @param targetCourseTaskStatus 目标课程任务状态
	 * @return
	 */
	int updateCourseTaskStatusByExpCourseStatus(Integer expCourseId, Byte targetCourseTaskStatus);
}
