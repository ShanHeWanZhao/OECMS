package com.trd.oecms.service;

import com.trd.oecms.model.ExpCourse;
import com.trd.oecms.query.ExpCourseQueryConditions;
import com.trd.oecms.utils.JsonResult;

import java.util.List;

/**
 * @author tanruidong
 * @date 2020-04-14 11:38
 */
public interface IExpCourseService {
    int insert(ExpCourse record);

    ExpCourse selectByPrimaryKey(Integer expCourseId);

    List<ExpCourse> selectAll();

    int updateByPrimaryKey(ExpCourse record);

    int insertBatch(List<ExpCourse> expCourseList);

    JsonResult listExpCourse(int offset, Integer pageSize, ExpCourseQueryConditions conditions);

    /**
     * 选择性的更新实验课程信息
     * @param expCourse 实验课程信息对象
     * @param needAddUploadCount 是否需要增加文件上传次数（只有在文件上传时才能设置为true）
     */
    void updateSelectiveById(ExpCourse expCourse, Boolean needAddUploadCount);
}
