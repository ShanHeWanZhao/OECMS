package com.trd.oecms.service;

import com.trd.oecms.model.CourseTask;
import com.trd.oecms.query.CourseTaskQueryConditions;
import com.trd.oecms.query.ExpCourseQueryConditions;
import com.trd.oecms.utils.JsonResult;

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
	JsonResult listCourseTask(int offset, Integer pageSize, CourseTaskQueryConditions conditions);
}
