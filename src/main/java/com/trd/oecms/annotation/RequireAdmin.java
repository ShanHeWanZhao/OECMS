package com.trd.oecms.annotation;

import java.lang.annotation.*;

/**
 * 要求是管理员
 * @author tanruidong
 * @date 2020-04-07 13:09
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireAdmin {
}
