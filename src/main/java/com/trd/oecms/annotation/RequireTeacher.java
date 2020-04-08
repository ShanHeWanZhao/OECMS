package com.trd.oecms.annotation;

import java.lang.annotation.*;

/**
 * 要求是老师
 * @author tanruidong
 * @date 2020-04-08 17:10
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireTeacher {
}
