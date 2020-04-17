package com.trd.oecms.query;

import lombok.Data;

/**
 * @author tanruidong
 * @date 2020-04-17 18:30
 */
@Data
public class CourseTaskQueryConditions {
    /**
     * 教师ID
     */
    private Integer teacherId;
    private String courseTaskStatus;
    private String expCourseName;
    private String studentName;
    private String className;
}
