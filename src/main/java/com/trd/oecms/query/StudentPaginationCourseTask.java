package com.trd.oecms.query;

import com.trd.oecms.model.CourseTask;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author tanruidong
 * @date 2020-04-21 14:47
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class StudentPaginationCourseTask extends CourseTask {
    private String expCourseName;
    private String expCourseLocation;
    private String expCourseMaterial;
    private Date expCourseTime;
    private String teacherName;
    private Byte expCourseStatus;
    private Byte materialUploadCount;
}
