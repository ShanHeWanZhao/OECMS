package com.trd.oecms.dao;

import com.trd.oecms.model.CourseTask;
import com.trd.oecms.query.TeacherPaginationCourseTask;
import com.trd.oecms.query.CourseTaskQueryConditions;
import org.apache.ibatis.annotations.Param;

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

	List<TeacherPaginationCourseTask> listCourseTask(@Param("offset") int offset, @Param("size") Integer pageSize, @Param("cond") CourseTaskQueryConditions conditions);

	int listCourseTaskCount(@Param("offset") int offset, @Param("size") Integer pageSize, @Param("cond") CourseTaskQueryConditions conditions);
}