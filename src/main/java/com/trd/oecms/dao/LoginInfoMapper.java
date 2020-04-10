package com.trd.oecms.dao;

import com.trd.oecms.entities.LoginInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Trd
 * @date 2020-04-06 19:04
 */
public interface LoginInfoMapper {
    int insert(LoginInfo record);

    LoginInfo selectByPrimaryKey(@Param("userId") Integer userId);

    List<LoginInfo> selectAll();

    int updateByPrimaryKey(LoginInfo record);

	LoginInfo getByLoginPage(@Param("accountNum") String accountNum,
							 @Param("password") String password,
							 @Param("userType") int ordinal);

    void updatePassword(@Param("id") Integer userId,@Param("password") String newPassword);
}