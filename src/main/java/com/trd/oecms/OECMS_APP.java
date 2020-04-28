package com.trd.oecms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author tanruidong
 * @date 2020-03-31 10:39
 */
@MapperScan("com.trd.oecms.dao")
@SpringBootApplication
@EnableTransactionManagement
public class OECMS_APP {
    public static void main(String[] args) {
        SpringApplication.run(OECMS_APP.class, args);
    }
}
