package com.trd.oecms.annotation;

import com.trd.oecms.entities.enums.UserInfoType;

import javax.validation.Constraint;
import com.trd.oecms.validator.EqualsCurrentUserValidator;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 当前用户的验证器
 * @author tanruidong
 * @date 2020-04-10 11:34
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EqualsCurrentUserValidator.class)
public @interface EqualsCurrentUser {

    /**
     * 检测当前用户的某项信息
     * @return
     */
    UserInfoType infoType() default UserInfoType.ID;

    /**
     * 是否要求密码相同
     * @return false -> 密码不能相同，修改密码时会用到
     */
    boolean requireSamePassword() default true;

    String message() default "你不是当前登录的用户！！";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * Defines several {@link EqualsCurrentUser} annotations on the same element.
     *
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE,  PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        EqualsCurrentUser[] value();
    }
}
