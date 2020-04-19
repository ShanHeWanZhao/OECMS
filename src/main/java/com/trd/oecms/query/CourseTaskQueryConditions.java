package com.trd.oecms.query;

import lombok.Data;

/**
 * 教师查询学生任务的条件类
 * @author tanruidong
 * @date 2020-04-17 18:30
 */
@Data
public class CourseTaskQueryConditions {
    /**
     * 教师ID
     */
    private Integer teacherId;
	/**
	 * 课程任务的状态
	 */
	private Byte courseTaskStatus;
	/**
	 * 实验课名称
	 */
    private String expCourseName;
	/**
	 * 学生姓名
	 */
	private String studentName;
	/**
	 * 班级名
	 */
    private String className;
}
