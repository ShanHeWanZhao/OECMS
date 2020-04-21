package com.trd.oecms.service.impl;

import com.trd.oecms.dao.ExpCourseMapper;
import com.trd.oecms.model.ExpCourse;
import com.trd.oecms.query.ExpCourseQueryConditions;
import com.trd.oecms.service.IExpCourseService;
import com.trd.oecms.utils.DateUtils;
import com.trd.oecms.utils.FileUtils;
import com.trd.oecms.utils.JsonResult;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author tanruidong
 * @date 2020-04-14 11:40
 */
@Service
@Slf4j
public class ExpCourseServiceImpl implements IExpCourseService {

    @Autowired
    private ExpCourseMapper expCourseMapper;

    // 讲义存放路径
    @Value("${experimentalCourse.resources.word.materials}")
    private String teachMaterialsPath;

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

    @Override
    public long uploadTeachMaterials(MultipartFile multipartFile, Integer expCourseId) throws Exception {
        long handleStartTime = System.currentTimeMillis();
        String pdfName = FileUtils.saveFile(multipartFile, teachMaterialsPath);
        // 构建更新器，只更新讲义存放路径（即讲义名）
        ExpCourse expCourse = new ExpCourse().setExpCourseId(expCourseId).setExpCourseMaterial(pdfName);
        this.updateSelectiveById(expCourse, true);
        long secondsDuration = DateUtils.getSecondsDuration(handleStartTime, System.currentTimeMillis());
        log.info("文件上传成功，上传人为【{}】，文件名为【{}】，处理时间为【{}】秒", UserUtil.getCurrentLoginInfo().getUserName(), multipartFile.getOriginalFilename(), secondsDuration);
        return secondsDuration;
    }


}
