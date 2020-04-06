package com.trd.oecms.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 实验课数据模型
 * 对应的数据库表名: exp_course
 * @author Trd
 * @date 2020-04-05 16:55
 */
@Data
@Accessors(chain = true)
public class ExpCourse implements Serializable {
    /**
     * 实验课程的id
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
     * 该实验课的任课老师id
     */
    private Integer teacherId;

    /**
     * 实验课的状态（未开始，进行中，已取消，已完成等）
     */
    private Byte expCourseStatus;

    /**
     * 该实验课的创建时间
     */
    private Date expCourseCreateTime;

    /**
     * 该实验课的类型，关联某个正式课程
     */
    private Integer formalCourseId;

    private static final long serialVersionUID = 1L;

}