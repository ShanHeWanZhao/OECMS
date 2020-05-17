package com.trd.oecms.query;

import com.trd.oecms.model.CourseTask;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 教师查询学生课程任务的Mybatis映射
 * @author Trd
 * @date 2020-04-19 13:01
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TeacherPaginationCourseTask extends CourseTask {
	private static final long serialVersionUID = 1L;
	private String userName;
	private Integer userClassId;
	private String expCourseName;
}
