package com.trd.oecms.dao;

import com.trd.oecms.entities.CourseTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Trd
 * @date 2020-04-05 17:42
 */
public interface CourseTaskMapper {

    int insert(CourseTask record);

    CourseTask selectByPrimaryKey(@Param("courseTaskId") Integer courseTaskId);

    List<CourseTask> selectAll();

    int updateByPrimaryKey(CourseTask record);
}