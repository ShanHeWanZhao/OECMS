package com.trd.oecms.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 学生查询实验任务的条件类
 * @author tanruidong
 * @date 2020-04-17 18:30
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CourseTaskQueryConditionsByStudent extends AbstractCourseTaskQueryConditions{
	/**
	 * 学生ID
	 */
	private Integer studentId;
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;
	/**
	 * 教师名
	 */
	private String teacherName;
	/**
	 * 实验地点
	 */
	private String expCourseLocation;
}
