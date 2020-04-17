package com.trd.oecms.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 对应的数据库表名: student_class
 * @author Trd
 * @date 2020-04-14 10:25
 */
@Data
@Accessors(chain = true)
public class StudentClass implements Serializable {
    /**
     * 班级的id
     */
    private Integer studentClassId;

    /**
     * 班级名字
     */
    private String className;

    /**
     * 班级状态（0 -> 可操作。1 -> 不可操作）
     */
    private Byte classStatus;

    /**
     * 班级创建时间
     */
    private Date classCreateTime;

    private static final long serialVersionUID = 1L;

}