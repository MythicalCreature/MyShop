package myshop.service;

import myshop.entity.User;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/5 18:36
 */
public interface UserService {
    boolean checkedUser(String username) throws SQLException;

    boolean registerUser(User user) throws SQLException;

    int activeUser(String code) throws SQLException;

    User loginUser(String username) throws SQLException;


    List<User> findAllByType(int state,String username, String sex) throws SQLException;

    List<User> findAllByState(int state) throws SQLException ;

    boolean deleteUser(int uid) throws SQLException;

    User findUserByUserName(String username) throws SQLException;
}
