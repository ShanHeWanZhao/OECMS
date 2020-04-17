package com.trd.oecms.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author tanruidong
 * @date 2020-04-16 14:21
 */
@Component
@Slf4j
public class initExpResourcesDirs implements ApplicationRunner {
    @Value("${experimentalCourse.resources.word.materials}")
    private String teachMaterialsPath;
    @Value("${experimentalCourse.resources.word.result_data}")
    private String resultDataPath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        File materialsDir = new File(teachMaterialsPath);
        File resultDir = new File(resultDataPath);
        if (materialsDir.mkdirs()){
            log.info("创建实验讲义存放路径文件夹成功，路径为：{}",materialsDir.getPath());
        }
        if (resultDir.mkdirs()){
            log.info("创建实验结果存放路径文件夹成功，路径为：{}",resultDir.getPath());
        }
    }
}
