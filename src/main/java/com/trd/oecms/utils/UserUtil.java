package com.trd.oecms.utils;

import com.trd.oecms.model.LoginInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author Trd
 * @date 2020-4-6 10:42
 */
public abstract class UserUtil {

	/**
	 * 登录信息在session中的属性名
	 */
    private static final String USER_IN_SESSION = "loginInfo";

	public static String getRequestURI(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return requestAttributes.getRequest().getRequestURI();
	}

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
    
	/**
	 * 向ModelAndView中保存LoginInfo数据
	 * @param pathName 
	 * @return
	 */
	public static ModelAndView getMv(String pathName){
		ModelAndView mv = new ModelAndView(pathName);
		mv.addObject("loginInfo", getCurrentLoginInfo());
		return mv;
	}
}
