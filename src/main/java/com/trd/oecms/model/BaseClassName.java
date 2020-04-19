package com.trd.oecms.model;

import lombok.Data;

/**
 * 班级名
 * @author tanruidong
 * @date 2020-04-16 17:08
 */
@Data
public class BaseClassName {
    /**
     * 用户班级名，仅当分页查询显示用户班级时该字段才会用上
     */
    protected String className;
}
