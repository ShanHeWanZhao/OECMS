package com.trd.oecms.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实验课程任务的模型
 * 对应的数据库表名: course_task
 * @author Trd
 * @date 2020-04-05 17:42
 */
@Data
@Accessors(chain = true)
public class CourseTask implements Serializable {
    /**
     * 课程任务的id
     */
    private Integer courseTaskId;

    /**
     * 实验课的id
     */
    private Integer expCourseId;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 任该实验课程的老师id
     */
    private Integer teacherId;

    /**
     * 实验课程报告的存放地址
     */
    private String expCourseReportPath;

    /**
     * 实验课程数据的存放地址
     */
    private String expCourseDataPath;

    /**
     * 该学生的该实验课的得分
     */
    private Double expCourseGrade;

    /**
     * 该学生的该实验课程完成状态
     */
    private Byte courseTaskStatus;

    /**
     * 创建时间
     */
    private Date courseTaskCreateTime;

    /**
     * 该学生的实验课程评语
     */
    private String courseTaskComment;

    private static final long serialVersionUID = 1L;

}