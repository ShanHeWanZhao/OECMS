package com.trd.oecms.dao;

import com.trd.oecms.entities.FormalCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Trd
 * @date 2020-04-05 17:03
 */
public interface FormalCourseMapper {

    int insert(FormalCourse record);

    FormalCourse selectByPrimaryKey(@Param("formalCourseId") Integer formalCourseId);

    List<FormalCourse> selectAll();

    int updateByPrimaryKey(FormalCourse record);
}