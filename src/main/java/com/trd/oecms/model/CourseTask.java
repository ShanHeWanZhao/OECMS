package com.trd.oecms.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 对应的数据库表名: course_task
 * @author Trd
 * @date 2020-04-14 10:25
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
     * 上课学生id
     */
    private Integer studentId;

    /**
     * 任课老师id
     */
    private Integer teacherId;

    /**
     * 实验课的结果数据的存放地址
     */
    private String expCourseResultData;

    /**
     * 该学生的该实验课的得分
     */
    private BigDecimal expCourseGrade;

    /**
     * 该学生的该实验课程完成状态（未开始，进行中，已提交，已完成）
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