package com.trd.oecms.service;

import com.trd.oecms.entities.LoginInfo;
import com.trd.oecms.entities.enums.UserTypeEnum;
import com.trd.oecms.exception.UserNotExistExcepion;

import java.util.List;

/**
 * @author Trd
 * @date 2020-04-06 20:38
 */
public interface ILoginInfoService {

	int save(LoginInfo record);

	LoginInfo getByUserId(Integer userId);

	List<LoginInfo> getAll();

	int updateByUserId(LoginInfo record);

	/**
	 * 获取LoginInfo对象
	 * @param accountNum 用户名
	 * @param password 密码
	 * @param userType 用户类型
	 * @return
	 */
	LoginInfo getUser(String accountNum, String password, UserTypeEnum userType) throws UserNotExistExcepion;
}
