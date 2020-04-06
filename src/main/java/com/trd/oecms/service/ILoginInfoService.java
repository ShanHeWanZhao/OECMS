package com.trd.oecms.service;

import com.trd.oecms.entities.LoginInfo;

import java.util.List;

/**
 * @author Trd
 * @date 2020-04-06 20:38
 */
public interface ILoginInfoService{

		int save(LoginInfo record);

		LoginInfo getByUserId(Integer userId);

		List<LoginInfo> getAll();

		int updateByUserId(LoginInfo record);
}
