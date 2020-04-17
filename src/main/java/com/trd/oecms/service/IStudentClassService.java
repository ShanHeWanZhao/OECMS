package com.trd.oecms.service;

import com.trd.oecms.model.StudentClass;

import java.util.List;

/**
 * @author tanruidong
 * @date 2020-04-14 12:00
 */
public interface IStudentClassService {
    int insert(StudentClass record);

    StudentClass selectByPrimaryKey(Integer studentClassId);

    List<StudentClass> selectAll();

    /**
     * 查询班级名对应的id
     * @param className 班级名
     * @return 班级id
     */
    Integer getIdByClassName(String className);

    int updateByPrimaryKey(StudentClass record);
}
