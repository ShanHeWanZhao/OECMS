package com.trd.oecms.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 教师查询学生任务的条件类
 * @author tanruidong
 * @date 2020-04-17 18:30
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CourseTaskQueryConditionsByTeacher extends AbstractCourseTaskQueryConditions{
    /**
     * 教师ID
     */
    private Integer teacherId;
	/**
	 * 学生姓名
	 */
	private String studentName;
	/**
	 * 班级名
	 */
    private String className;
}
