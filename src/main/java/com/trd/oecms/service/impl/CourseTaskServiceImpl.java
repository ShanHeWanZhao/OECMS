package com.trd.oecms.service.impl;

import com.trd.oecms.dao.CourseTaskMapper;
import com.trd.oecms.model.CourseTask;
import com.trd.oecms.service.ICourseTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanruidong
 * @date 2020-04-17 17:14
 */
@Service
public class CourseTaskServiceImpl implements ICourseTaskService {
    @Autowired
    private CourseTaskMapper courseTaskMapper;

    @Override
    public int insert(CourseTask record) {
        return courseTaskMapper.insert(record);
    }

    @Override
    public CourseTask selectByPrimaryKey(Integer courseTaskId) {
        return courseTaskMapper.selectByPrimaryKey(courseTaskId);
    }

    @Override
    public List<CourseTask> selectAll() {
        return courseTaskMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(CourseTask record) {
        return courseTaskMapper.updateByPrimaryKey(record);
    }

    @Override
    public int insertBatch(List<CourseTask> courseTaskList) {
        return courseTaskMapper.insertBatch(courseTaskList);
    }

}
