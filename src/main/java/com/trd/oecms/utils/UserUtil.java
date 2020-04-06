package com.trd.oecms.utils;

import com.trd.oecms.entities.LoginInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Trd
 * @date 2019-12-08 23:42
 */
public class UserUtil {
	/**
	 * 登录信息在session中的属性名
	 */
    public static final String USER_IN_SESSION = "loginInfo";

	/**
	 * 获取session <p/>
	 * 注意：每次获取session应该保证是这个request的，是变化的，是这个request请求中的session
	 * @return
	 */
    public static HttpSession getSession(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return requestAttributes.getRequest().getSession();
	}

	/**
	 * 向session中保存用户的登录信息
	 * @param loginInfo
	 */
    public static void setCurrentLoginInfo(LoginInfo loginInfo){
        getSession().setAttribute(USER_IN_SESSION,loginInfo);
    }

	/**
	 *  从session中获取用户的登录信息
	 * @return
	 */
	public static LoginInfo getCurrentLoginInfo(){
        return (LoginInfo) getSession().getAttribute(USER_IN_SESSION);
    }

}
