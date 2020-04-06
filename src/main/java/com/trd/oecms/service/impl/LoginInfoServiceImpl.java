package com.trd.oecms.service.impl;

import com.trd.oecms.dao.LoginInfoMapper;
import com.trd.oecms.entities.LoginInfo;
import com.trd.oecms.service.ILoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Trd
 * @date 2020-04-06 20:41
 */
@Service
public class LoginInfoServiceImpl implements ILoginInfoService {
	@Autowired
	private LoginInfoMapper loginInfoMapper;

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
}
