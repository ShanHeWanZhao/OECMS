package com.trd.oecms.service.impl;

import com.trd.oecms.dao.CourseTaskMapper;
import com.trd.oecms.model.CourseTask;
import com.trd.oecms.query.TeacherPaginationCourseTask;
import com.trd.oecms.query.CourseTaskQueryConditions;
import com.trd.oecms.service.ICourseTaskService;
import com.trd.oecms.utils.JsonResult;
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

	@Override
	public JsonResult listCourseTask(int offset, Integer pageSize, CourseTaskQueryConditions conditions) {
		try{
			List<TeacherPaginationCourseTask> taskList = courseTaskMapper.listCourseTask(offset, pageSize, conditions);
			int count = courseTaskMapper.listCourseTaskCount(offset, pageSize, conditions);
			JsonResult jsonResult = JsonResult.ok();
			if (taskList != null && taskList.size() > 0){
				jsonResult.setData(taskList);
				jsonResult.setCount(count);
			}
			return jsonResult;
		}catch(Exception e){
			e.printStackTrace();
			return JsonResult.error(e.getMessage());
		}
	}

}
