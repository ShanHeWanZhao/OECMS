package com.trd.oecms.service.impl;

import com.trd.oecms.constants.enums.UserTypeEnum;
import com.trd.oecms.dao.LoginInfoMapper;
import com.trd.oecms.exception.UserNotExistException;
import com.trd.oecms.model.LoginInfo;
import com.trd.oecms.service.ILoginInfoService;
import com.trd.oecms.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author Trd
 * @date 2020-04-06 20:41
 */
@Service
@Slf4j
public class LoginInfoServiceImpl implements ILoginInfoService {

	private final LoginInfoMapper loginInfoMapper;

	public LoginInfoServiceImpl(LoginInfoMapper loginInfoMapper) {
		this.loginInfoMapper = loginInfoMapper;
	}

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
		return loginInfoMapper.updateSelectiveById(record);
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
    public void updateSelectiveById(LoginInfo loginInfo) {
        loginInfoMapper.updateSelectiveById(loginInfo);
    }

    @Override
    public int insertBatch(List<LoginInfo> loginInfoList) {
        return loginInfoMapper.insertBatch(loginInfoList);
    }

    @Override
    public Integer getIdByTeacherName(String teacherName) {
		LoginInfo teacherInfo = loginInfoMapper.getIdByTeacherName(teacherName);
		Assert.notNull(teacherInfo, "名为【"+teacherName+"】的教师不存在，请仔细检查");
		System.out.println(teacherInfo);
		if (teacherInfo.getUserStatus() == 1){
			throw new IllegalArgumentException(teacherInfo.getUserName()+"，该老师状态异常，请联系管理员");
		}
		return teacherInfo.getUserId();
	}

    @Override
    public JsonResult listExcludeAdmin(int offset, Integer pageSize, LoginInfo loginInfo) {
		try{
			List<LoginInfo> infoList = loginInfoMapper.listExcludeAdmin(offset, pageSize, loginInfo);
			int count = loginInfoMapper.listExcludeAdminCount(offset, pageSize, loginInfo);
			JsonResult jsonResult = JsonResult.ok();
			Optional.ofNullable(infoList).
					filter(value -> value.size() > 0).
					ifPresent(value -> {
						jsonResult.setData(value);
						jsonResult.setCount(count);
					});
			return jsonResult;
		}catch(Exception e){
			e.printStackTrace();
			return JsonResult.error(e.getMessage());
		}
    }

	@Override
	public List<Integer> getStudentIdByClassId(Integer studentClassId) {
		return loginInfoMapper.getStudentIdByClassId(studentClassId);
	}

    @Override
    public int getCountByAccountNumber(String accountNumber) {
        return loginInfoMapper.getCountByAccountNumber(accountNumber);
    }
}
