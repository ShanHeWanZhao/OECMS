package com.trd.oecms.query;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 实验课程分页查询的条件
 * @author tanruidong
 * @date 2020-04-16 16:42
 */
@Data
public class ExpCourseQueryConditions {
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
     * 教师ID
     */
    private Integer teacherId;
    /**
     * 课程状态
     */
    private Byte expCourseStatus;
    /**
     * 课程名
     */
    private String className;
    /**
     * 上课地点
     */
    private String expCourseLocation;
}
