package com.trd.oecms.dao;

import com.trd.oecms.model.CourseTask;
import com.trd.oecms.query.CourseTaskQueryConditionsByStudent;
import com.trd.oecms.query.StudentPaginationCourseTask;
import com.trd.oecms.query.TeacherPaginationCourseTask;
import com.trd.oecms.query.CourseTaskQueryConditionsByTeacher;
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

	List<TeacherPaginationCourseTask> listCourseTaskByTeacher(@Param("offset") int offset,
                                                              @Param("size") Integer pageSize,
                                                              @Param("cond") CourseTaskQueryConditionsByTeacher conditions);

	int listCourseTaskCountByTeacher(@Param("offset") int offset,
                                     @Param("size") Integer pageSize,
                                     @Param("cond") CourseTaskQueryConditionsByTeacher conditions);

    void updateSelectiveById(@Param("task") CourseTask newCourseTask,
                             @Param("end") Boolean isTaskEnd,
                             @Param("addCount") Boolean isNeedAddResultDataCount);

    List<StudentPaginationCourseTask> listCourseTaskByStudent(@Param("offset") int offset,
                                                              @Param("size") Integer pageSize,
                                                              @Param("cond") CourseTaskQueryConditionsByStudent conditions);

    int listCourseTaskCountByStudent(@Param("offset") int offset,
                                     @Param("size") Integer pageSize,
                                     @Param("cond") CourseTaskQueryConditionsByStudent conditions);

    int updateCourseTaskStatusByExpCourseStatus(@Param("id") Integer expCourseId, @Param("status") Byte targetCourseTaskStatus);
}