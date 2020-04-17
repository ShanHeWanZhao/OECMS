package com.trd.oecms.dao;

import com.trd.oecms.model.LoginInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Trd
 * @date 2020-04-06 19:04
 */
public interface LoginInfoMapper {
    /**
     * 插入一条记录
     * @param record
     * @return
     */
    int insert(LoginInfo record);

    LoginInfo selectByPrimaryKey(@Param("userId") Integer userId);

    List<LoginInfo> selectAll();

    /**
     * 根据给定的更新内容来更新登录信息
     * @param loginInfo
     * @return
     */
    int updateSelectiveById(LoginInfo loginInfo);

	LoginInfo getByLoginPage(@Param("accountNum") String accountNum,
							 @Param("password") String password,
							 @Param("userType") int ordinal);

    int insertBatch(@Param("loginInfoList") List<LoginInfo> loginInfoList);

    LoginInfo getIdByTeacherName(String teacherName);

    List<LoginInfo> listExcludeAdmin(@Param("offset") int offset, @Param("size") Integer pageSize, @Param("info") LoginInfo loginInfo);

    int listExcludeAdminCount(@Param("offset") int offset, @Param("size") Integer pageSize, @Param("info") LoginInfo loginInfo);


    List<Integer> getStudentIdByClassId(@Param("classId") Integer studentClassId);
}