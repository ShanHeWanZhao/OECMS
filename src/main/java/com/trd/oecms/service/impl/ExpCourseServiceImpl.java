package com.trd.oecms.service.impl;

import com.trd.oecms.dao.ExpCourseMapper;
import com.trd.oecms.model.ExpCourse;
import com.trd.oecms.query.ExpCourseQueryConditions;
import com.trd.oecms.service.IExpCourseService;
import com.trd.oecms.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanruidong
 * @date 2020-04-14 11:40
 */
@Service
public class ExpCourseServiceImpl implements IExpCourseService {
    @Autowired
    private ExpCourseMapper expCourseMapper;

    @Override
    public int insert(ExpCourse expCourse) {
        return expCourseMapper.insert(expCourse);
    }

    @Override
    public ExpCourse selectByPrimaryKey(Integer expCourseId) {
        return expCourseMapper.selectByPrimaryKey(expCourseId);
    }

    @Override
    public List<ExpCourse> selectAll() {
        return expCourseMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(ExpCourse expCourse) {
        return expCourseMapper.updateByPrimaryKey(expCourse);
    }

    @Override
    public int insertBatch(List<ExpCourse> expCourseList) {
        return expCourseMapper.insertBatch(expCourseList);
    }

    @Override
    public JsonResult listExpCourse(int offset, Integer pageSize, ExpCourseQueryConditions conditions) {
        try{
            List<ExpCourse> expCourseList = expCourseMapper.listExpCourse(offset, pageSize, conditions);
            int count = expCourseMapper.listExpCourseCount(offset, pageSize, conditions);
            JsonResult jsonResult = JsonResult.ok();
            if (expCourseList != null && expCourseList.size() > 0){
                jsonResult.setData(expCourseList);
                jsonResult.setCount(count);
            }
            return jsonResult;
        }catch(Exception e){
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    @Override
    public void updateSelectiveById(ExpCourse expCourse, Boolean needAddUploadCount) {
        expCourseMapper.updateSelectiveById(expCourse, needAddUploadCount);
    }


}
