package com.trd.oecms.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tanruidong
 * @date 2020-04-15 15:25
 */
@Controller
@RequestMapping("word")
@Slf4j
public class WordController {
    @Value("${experimentalCourse.resources.word.result_data}")
    private String resultDataPath;
}
