package com.trd.oecms.query;

import lombok.Data;

/**
 * @author tanruidong
 * @date 2020-04-20 12:20
 */
@Data
public abstract class AbstractCourseTaskQueryConditions {
    /**
     * 课程任务的状态
     */
    protected Byte courseTaskStatus;
    /**
     * 实验课名称
     */
    protected String expCourseName;
}
