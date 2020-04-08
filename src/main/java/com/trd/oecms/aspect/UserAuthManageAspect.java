package com.trd.oecms.aspect;

import com.trd.oecms.entities.LoginInfo;
import com.trd.oecms.entities.enums.UserTypeEnum;
import com.trd.oecms.exception.AuthNotPassException;
import com.trd.oecms.utils.UserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author tanruidong
 * @date 2020-04-08 18:31
 */
@Aspect
@Component
public class UserAuthManageAspect {

    /**
     * 学生权限管理
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.trd.oecms.annotation.RequireStudent)")
    public Object requireStudent(ProceedingJoinPoint joinPoint) throws Throwable{
        LoginInfo loginInfo = UserUtil.getCurrentLoginInfo();
        boolean isTrue = UserTypeEnum.isTargetType(loginInfo.getUserType(), UserTypeEnum.STUDENT);
        if (!isTrue){
            throw new AuthNotPassException("你没有学生的权限，请勿进入");
        }
        return joinPoint.proceed();
    }
}
