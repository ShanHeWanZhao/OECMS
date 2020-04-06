package com.trd.oecms.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 对应的数据库表名: formal_course
 * @author Trd
 * @date 2020-04-05 17:03
 */
@Data
@Accessors(chain = true)
public class FormalCourse implements Serializable {
    /**
     * 正式课的id
     */
    private Integer formalCourseId;

    /**
     * 正式课的名字
     */
    private String formalCourseName;

    /**
     * 正式课的任课老师id
     */
    private Integer teacherId;

    /**
     * 正式课的创建时间
     */
    private Date formalCourseCreateTime;

    /**
     * 正式课的状态
     */
    private Byte formalCourseStatus;

    private static final long serialVersionUID = 1L;

}