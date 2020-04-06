package com.trd.oecms.dao;

import com.trd.oecms.entities.ExpCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Trd
 * @date 2020-04-05 16:55
 */
public interface ExpCourseMapper {

    int insert(ExpCourse record);

    ExpCourse selectByPrimaryKey(@Param("expCourseId") Integer expCourseId);

    List<ExpCourse> selectAll();

    int updateByPrimaryKey(ExpCourse record);
}