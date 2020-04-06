package com.trd.oecms.service.impl;

import com.trd.oecms.dao.LoginInfoMapper;
import com.trd.oecms.entities.LoginInfo;
import com.trd.oecms.entities.enums.UserTypeEnum;
import com.trd.oecms.exception.UserNotExistExcepion;
import com.trd.oecms.service.ILoginInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Trd
 * @date 2020-04-06 20:41
 */
@Service
public class LoginInfoServiceImpl implements ILoginInfoService {
	@Autowired
	private LoginInfoMapper loginInfoMapper;

	private final Logger logger = LoggerFactory.getLogger(LoginInfoServiceImpl.class);

	@Override
	public int save(LoginInfo record) {
		return loginInfoMapper.insert(record);
	}

	@Override
	public LoginInfo getByUserId(Integer userId) {
		return loginInfoMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<LoginInfo> getAll() {
		return loginInfoMapper.selectAll();
	}

	@Override
	public int updateByUserId(LoginInfo record) {
		return loginInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public LoginInfo getUser(String username, String password, UserTypeEnum userType) throws UserNotExistExcepion {
		LoginInfo info = loginInfoMapper.getByLoginPage(username, password, userType.ordinal());
		if (info == null){
			logger.error("账号为【{}】的用户登录失败，密码为【{}】，类型为【{}】",
					username, password, userType.getUserTypeName());
			throw new UserNotExistExcepion("账号为【"+username+"】的用户登录失败");
		} else {
			return info;
		}
	}
}
