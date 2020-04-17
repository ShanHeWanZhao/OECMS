package com.trd.oecms.dao;

import com.trd.oecms.model.ExpCourse;
import com.trd.oecms.query.ExpCourseQueryConditions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Trd
 * @date 2020-04-14 10:25
 */
public interface ExpCourseMapper {
    int insert(ExpCourse expCourse);

    ExpCourse selectByPrimaryKey(Integer expCourseId);

    List<ExpCourse> selectAll();

    int updateByPrimaryKey(ExpCourse record);

    int insertBatch( List<ExpCourse> expCourseList);

    List<ExpCourse> listExpCourse(@Param("offset") int offset, @Param("size") Integer pageSize, @Param("cond") ExpCourseQueryConditions conditions);

    int listExpCourseCount(@Param("offset") int offset, @Param("size") Integer pageSize, @Param("cond") ExpCourseQueryConditions conditions);


    void updateSelectiveById(@Param("course") ExpCourse expCourse, @Param("addCount") Boolean needAddUploadCount);
}