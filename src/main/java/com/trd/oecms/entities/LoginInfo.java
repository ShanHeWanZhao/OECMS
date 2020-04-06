package com.trd.oecms.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 对应的数据库表名: login_info
 * @author Trd
 * @date 2020-04-06 19:04
 */
@Data
@Accessors(chain = true)
public class LoginInfo implements Serializable {
    /**
     * 登录账号的id
     */
    private Integer userId;

    /**
     * 账号
     */
    private String accountNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号创建时间
     */
    private Date createTime;

    /**
     * 用户的状态（0 -> 可登录，1 -> 不可登陆）
     */
    private Byte userStatus;

    /**
     * 用户的真实姓名
     */
    private String userName;

    /**
     * 用户的类型（学生->0，老师->1，管理员->2）
     */
    private Byte userType;

    private static final long serialVersionUID = 1L;
}