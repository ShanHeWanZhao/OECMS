package com.trd.oecms.validator;

import com.trd.oecms.annotation.EqualsCurrentUser;
import com.trd.oecms.model.LoginInfo;
import com.trd.oecms.constants.enums.LoginInfoTypeEnum;
import com.trd.oecms.utils.UserUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * EqualsCurrentUser注解的校验器
 * @author tanruidong
 * @date 2020-04-10 11:48
 */
public class EqualsCurrentUserValidator implements ConstraintValidator<EqualsCurrentUser, Object> {

    private LoginInfoTypeEnum infoType;
    private boolean requireSamePassword;

    @Override
    public void initialize(EqualsCurrentUser constraintAnnotation) {
        infoType = constraintAnnotation.infoType();
        requireSamePassword = constraintAnnotation.requireSamePassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LoginInfo loginInfo = UserUtil.getCurrentLoginInfo();
        switch (infoType) {
            case ID:
                return value.equals(loginInfo.getUserId());
            case ACCOUNT_NUMBER:
                return value.equals(loginInfo.getAccountNumber());
            case PASSWORD:
                return requireSamePassword == (value.equals(loginInfo.getPassword()));
            case USER_NAME:
                return value.equals(loginInfo.getUserName());
            case USER_TYPE:
                return value.equals(loginInfo.getUserType());
            case USER_STATUS:
                return value.equals(loginInfo.getUserStatus());
            // 创建时间
            default:
                return value.equals(loginInfo.getCreateTime());
        }
    }


}
