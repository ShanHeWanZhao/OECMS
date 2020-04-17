package com.trd.oecms.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 对应的数据库表名: exp_course
 * @author Trd
 * @date 2020-04-14 10:25
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ExpCourse extends BaseClassName implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 实验课程id
     */
    private Integer expCourseId;

    /**
     * 实验课名称
     */
    private String expCourseName;

    /**
     * 实验课的基本描述
     */
    private String expCourseDescription;

    /**
     * 上课地点
     */
    private String expCourseLocation;

    /**
     * 上课时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expCourseTime;

    /**
     * 任课老师id
     */
    private Integer teacherId;

    /**
     * 上课班级id
     */
    private Integer studentClassId;

    /**
     * 实验课程状态（未开始，进行中，已取消，已结束）
     */
    private Byte expCourseStatus;

    /**
     * 课程讲义存放的路径
     */
    private String expCourseMaterial;

    /**
     * 实验讲义上传的次数（最多上传3次）
     */
    private Byte materialUploadCount;

    /**
     * 创建时间
     */
    private Date expCourseCreateTime;

    public ExpCourse() {
    }

    public ExpCourse(String expCourseName, String expCourseLocation, Byte expCourseStatus, Date expCourseCreateTime) {
        this.expCourseName = expCourseName;
        this.expCourseLocation = expCourseLocation;
        this.expCourseStatus = expCourseStatus;
        this.expCourseCreateTime = expCourseCreateTime;
    }
}