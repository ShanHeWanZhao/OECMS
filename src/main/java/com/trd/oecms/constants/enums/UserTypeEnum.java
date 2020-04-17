package com.trd.oecms.constants.enums;

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
	public static UserTypeEnum getByNumber(Byte type){
		switch (type){
			case 0:
				return UserTypeEnum.STUDENT;
			case 1:
				return UserTypeEnum.TEACHER;
			case 2:
				return UserTypeEnum.ADMIN;
			default:
				throw new IllegalArgumentException("没有【"+type+"】这样的用户类型");
		}
	}

	/**
	 * 判断数字的用户类型是否是指定的枚举用户类型
	 * @param type 数字
	 * @param userTypeEnum 枚举
	 * @return
	 */
	public static boolean isTargetType(Byte type, UserTypeEnum userTypeEnum) {
		return type == userTypeEnum.ordinal();
	}

	/**
	 * 根据用户类型名获取对应的UserTypeEnum对象
	 * @param userTypeName
	 * @return
	 */
	public static UserTypeEnum getByUserTypeName(String userTypeName) {
		switch (userTypeName){
			case "学生":
				return UserTypeEnum.STUDENT;
			case "教师":
				return UserTypeEnum.TEACHER;
			default:
				throw new IllegalArgumentException("不存在【"+userTypeName+"】用户类型");
		}
	}
}
