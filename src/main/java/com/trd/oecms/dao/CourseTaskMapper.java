package com.trd.oecms.dao;

import com.trd.oecms.model.CourseTask;
import java.util.List;

/**
 * @author Trd
 * @date 2020-04-14 10:25
 */
public interface CourseTaskMapper {
    int insert(CourseTask record);

    CourseTask selectByPrimaryKey(Integer courseTaskId);

    List<CourseTask> selectAll();

    int updateByPrimaryKey(CourseTask record);

    int insertBatch(List<CourseTask> courseTaskList);
}