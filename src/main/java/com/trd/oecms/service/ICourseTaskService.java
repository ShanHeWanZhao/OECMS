package com.trd.oecms.service;

import com.trd.oecms.model.CourseTask;

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
}
