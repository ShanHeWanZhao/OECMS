package com.trd.oecms.dao;

import com.trd.oecms.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanruidong
 * @date 2020-03-31 11:46
 */
@Repository
public interface StudentDao {
    int insertOne(Student student);
    int deleteByPrimaryKey(@Param("id") int id);
    int updateByPrimaryKey(Student student);
    Student findByPrimaryKey(@Param("id") int id);
    List<Student> findAll();
}
