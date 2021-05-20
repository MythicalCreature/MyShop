package myshop.dao;

import myshop.entity.User;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/5 18:43
 */
public interface UserDao {
    User selectUser(String username) throws SQLException;

    int insertUser(User user) throws SQLException;

    int updateStatusByUid(int uid)throws SQLException;

    User selectUserByCode(String code) throws SQLException;

    List<User> selectUserListByType(String username, String sex) throws SQLException;

    List<User> selectUserListByState(int state)throws SQLException;

    int deleteUser(int uid) throws SQLException;

    User selectUserByUid(int uid) throws SQLException;
}
