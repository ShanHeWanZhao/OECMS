package com.trd.oecms.service.impl;

import com.trd.oecms.dao.LoginInfoMapper;
import com.trd.oecms.entities.LoginInfo;
import com.trd.oecms.entities.enums.UserTypeEnum;
import com.trd.oecms.exception.UserNotExistException;
import com.trd.oecms.service.ILoginInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Trd
 * @date 2020-04-06 20:41
 */
@Service
@Slf4j
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

	@Override
	public LoginInfo getUser(String accountNum, String password, UserTypeEnum userType) throws UserNotExistException {
		LoginInfo info = loginInfoMapper.getByLoginPage(accountNum, password, userType.ordinal());
		if (info == null){
			log.error("账号为【{}】的用户登录失败，密码为【{}】，类型为【{}】",
					accountNum, password, userType.getUserTypeName());
			throw new UserNotExistException("该用户不存在，请仔细检查输入信息是否正确！！");
		}else if(info.getUserStatus() != 0){
			throw new IllegalStateException("该用户当前不允许登录，请联系管理员");
		} else {
			return info;
		}
	}

    @Override
    public void updatePassword(Integer userId, String newPassword) {
        loginInfoMapper.updatePassword(userId, newPassword);
    }
}
