package com.trd.oecms.entities.enums;

import com.trd.oecms.exception.UserNotExistExcepion;

/**
 *  登录用户的类型
 * @author tanruidong
 * @date 2020-04-01 17:16
 */
public enum UserTypeEnum {
	/**
	 * 学生
	 */
    STUDENT("学生"),
	/**
	 * 教师
	 */
    TEACHER("教师"),
	/**
	 * 管理员
	 */
    ADMIN("管理员");

    private String userTypeName;

	UserTypeEnum(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	/**
	 * 根据数字获取对应的UserTypeEnum对象
	 * @param type
	 * @return
	 */
	public static UserTypeEnum getByNumber(Integer type) throws UserNotExistExcepion {
		switch (type){
			case 0:
				return UserTypeEnum.STUDENT;
			case 1:
				return UserTypeEnum.TEACHER;
			case 2:
				return UserTypeEnum.ADMIN;
			default:
					throw new UserNotExistExcepion("没有这样的用户类型");
		}
	}
}
