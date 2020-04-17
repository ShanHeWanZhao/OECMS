package com.trd.oecms.dao;

import com.trd.oecms.model.StudentClass;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Trd
 * @date 2020-04-14 10:25
 */
public interface StudentClassMapper {
    int insert(StudentClass record);

    StudentClass selectByPrimaryKey(Integer studentClassId);

    List<StudentClass> selectAll();

    int updateByPrimaryKey(StudentClass record);

    Integer getIdByClassName(@Param("className") String className);
}