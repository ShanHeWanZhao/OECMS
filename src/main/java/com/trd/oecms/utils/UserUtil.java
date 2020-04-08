package com.trd.oecms.utils;

import com.trd.oecms.entities.LoginInfo;
import com.trd.oecms.entities.Menu;
import com.trd.oecms.entities.enums.UserTypeEnum;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Trd
 * @date 2019-12-08 23:42
 */
public class UserUtil {

	private UserUtil(){

	}
	/**
	 * 登录信息在session中的属性名
	 */
    private static final String USER_IN_SESSION = "loginInfo";

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
	 * 获取HttpServletResponse对象
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return requestAttributes.getResponse();
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
	 * 向model中保存LoginInfo和menu数据
	 * @param model
	 */
	public static void addMenuInfo(Model model){
		LoginInfo loginInfo = getCurrentLoginInfo();
		List<Menu> menus = new ArrayList<>();
		switch (UserTypeEnum.getByNumber(loginInfo.getUserType())){
			case STUDENT:
				List<Menu> subMenus1 = new ArrayList<>();
				subMenus1.add(new Menu("走神", "/zoushen"));
				subMenus1.add(new Menu("听歌", "/tingge"));
				subMenus1.add(new Menu("玩游戏", "/wanyouxi"));
				Menu menu1 = new Menu("上课日常活动", "/huodong");
				menu1.setChildren(subMenus1);
				menus.add(menu1);
				menus.add(new Menu("交作业", "/zuoye"));
				menus.add(new Menu("上实验课", "/shiyanke"));
				break;
			case TEACHER:
				List<Menu> subMenus2 = new ArrayList<>();
				subMenus2.add(new Menu("讲课", "/teach"));
				subMenus2.add(new Menu("布置作业", "/布置作业"));
				Menu menu2 = new Menu("课堂", "/class");
				menu2.setChildren(subMenus2);
				menus.add(menu2);
				menus.add(new Menu("处理作业", "/handleWork"));
				menus.add(new Menu("查看实验课", "/seeExpCourse"));
				break;
			case ADMIN:
				List<Menu> subMenus3 = new ArrayList<>();
				subMenus3.add(new Menu("qqqqq", "/qqqqqq"));
				subMenus3.add(new Menu("wwwwwww", "/wwwwwww"));
				Menu menu3 = new Menu("管理大家", "/manageAll");
				menu3.setChildren(subMenus3);
				menus.add(menu3);
				menus.add(new Menu("发布账号", "/excel/index"));
				menus.add(new Menu("上传课程", "/uploadCourse"));
				break;
		}
		model.addAttribute("menus", menus);
		model.addAttribute("loginInfo", loginInfo);
	}

}
