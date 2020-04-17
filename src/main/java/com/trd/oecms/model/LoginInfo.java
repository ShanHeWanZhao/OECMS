package com.trd.oecms.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * 对应的数据库表名: login_info
 * @author Trd
 * @date 2020-04-06 19:04
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LoginInfo extends BaseClassName implements Serializable {
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

    /**
     * 用户对应的班级id（为-1时代表该用户无班级）
     */
    private Integer userClassId;

    private static final long serialVersionUID = 1L;

    public LoginInfo() {
    }

    public LoginInfo(String userName, String accountNumber, String password, Date createTime, Byte userStatus, Integer userClassId) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.createTime = createTime;
        this.userStatus = userStatus;
        this.userName = userName;
        this.userClassId = userClassId;
    }
}