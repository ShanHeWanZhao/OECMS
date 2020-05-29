package com.trd.oecms.aspect;

import com.trd.oecms.constants.enums.UserTypeEnum;
import com.trd.oecms.exception.AuthNotPassException;
import com.trd.oecms.model.LoginInfo;
import com.trd.oecms.utils.DateUtils;
import com.trd.oecms.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author tanruidong
 * @date 2020-04-08 18:31
 */
@Aspect
@Component
@Slf4j
public class UserAuthManageAspect {

    /**
     * 学生权限检查
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.trd.oecms.annotation.RequireStudent)")
    public Object requireStudent(ProceedingJoinPoint joinPoint) throws Throwable{
        if (userNotMatch(UserTypeEnum.STUDENT)){
        	printLog();
            throw new AuthNotPassException("你没有学生的权限，请勿进入");
        }
        return joinPoint.proceed();
    }

	/**
	 * 教师权限检查
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.trd.oecms.annotation.RequireTeacher)")
	public Object requireTeacher(ProceedingJoinPoint joinPoint) throws Throwable{
		if (userNotMatch(UserTypeEnum.TEACHER)){
			printLog();
			throw new AuthNotPassException("你没有教师的权限，请勿进入");
		}
		return joinPoint.proceed();
	}

	/**
	 * 管理员权限检查
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.trd.oecms.annotation.RequireAdmin)")
	public Object requireAdmin(ProceedingJoinPoint joinPoint) throws Throwable{
		if (userNotMatch(UserTypeEnum.ADMIN)){
			printLog();
			throw new AuthNotPassException("你没有管理员的权限，请勿进入");
		}
		return joinPoint.proceed();
	}

	/**
	 * 为true时，表示不匹配
	 * @param type 用户类型
	 * @return
	 */
    private boolean userNotMatch(UserTypeEnum type){
		LoginInfo loginInfo = UserUtil.getCurrentLoginInfo();
		return !UserTypeEnum.isTargetType(loginInfo.getUserType(), type);
	}

	private void printLog(){
		log.error("姓名为【{}】的试图访问无权限的内容。uri:【{}】。时间【{}】",
				UserUtil.getCurrentLoginInfo().getUserName(),
				UserUtil.getRequestURI(),
				DateUtils.parseDateToString(new Date()));
	}
}
