package com.trd.oecms.service;

import com.trd.oecms.model.LoginInfo;
import com.trd.oecms.constants.enums.UserTypeEnum;
import com.trd.oecms.exception.UserNotExistException;
import com.trd.oecms.utils.JsonResult;

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
	LoginInfo getUser(String accountNum, String password, UserTypeEnum userType) throws UserNotExistException;

	/**
	 * 选择性的更新用户信息
	 * @param loginInfo
	 */
    void updateSelectiveById(LoginInfo loginInfo);

	/**
	 * 批量插入登录信息
	 * @param loginInfoList
	 * @return 插入成功的数量
	 */
	int insertBatch(List<LoginInfo> loginInfoList);

	/**
	 * 通过教师名字查询其id
	 * @param teacherName
	 * @return
	 */
    Integer getIdByTeacherName(String teacherName);

	/**
	 * 分页查询除管理员之外的所有用户（可包括条件查询）
	 * @param offset 起始行
	 * @param pageSize 查询的数量
	 * @param loginInfo 条件查询信息
	 * @return
	 */
    JsonResult listExcludeAdmin(int offset, Integer pageSize, LoginInfo loginInfo);

	List<Integer> getStudentIdByClassId(Integer studentClassId);
}
