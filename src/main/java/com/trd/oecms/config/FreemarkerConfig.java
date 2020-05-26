package com.trd.oecms.config;

import cn.org.rapid_framework.freemarker.directive.BlockDirective;
import cn.org.rapid_framework.freemarker.directive.ExtendsDirective;
import cn.org.rapid_framework.freemarker.directive.OverrideDirective;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;



/**
 * FreeMarker模板引擎：开启block、extends、override的使用
 * @author tanruidong
 * @date 2020-03-31 17:04
 */
@Configuration // 配置类
@ConditionalOnWebApplication // 是web应用
public class FreemarkerConfig {

    private final freemarker.template.Configuration configuration;

    public FreemarkerConfig(freemarker.template.Configuration configuration) {
        this.configuration = configuration;
    }

    @PostConstruct
    public void setSharedVariable(){
        configuration.setSharedVariable("block", new BlockDirective());
        configuration.setSharedVariable("override", new OverrideDirective());
        configuration.setSharedVariable("extends", new ExtendsDirective());
    }
}
