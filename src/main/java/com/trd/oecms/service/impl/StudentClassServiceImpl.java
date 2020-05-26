package com.trd.oecms.service.impl;

import com.trd.oecms.dao.StudentClassMapper;
import com.trd.oecms.model.StudentClass;
import com.trd.oecms.service.IStudentClassService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanruidong
 * @date 2020-04-14 12:00
 */
@Service
public class StudentClassServiceImpl implements IStudentClassService {

    private final StudentClassMapper studentClassMapper;

    public StudentClassServiceImpl(StudentClassMapper studentClassMapper) {
        this.studentClassMapper = studentClassMapper;
    }

    @Override
    public int insert(StudentClass studentClass) {
        return studentClassMapper.insert(studentClass);
    }

    @Override
    public StudentClass selectByPrimaryKey(Integer studentClassId) {
        return studentClassMapper.selectByPrimaryKey(studentClassId);
    }

    @Override
    public List<StudentClass> selectAll() {
        return studentClassMapper.selectAll();
    }

    @Override
    public Integer getIdByClassName(String className) {
        return studentClassMapper.getIdByClassName(className);
    }

    @Override
    public int updateByPrimaryKey(StudentClass studentClass) {
        return studentClassMapper.updateByPrimaryKey(studentClass);
    }
}
