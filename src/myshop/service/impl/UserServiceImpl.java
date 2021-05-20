package myshop.service.impl;

import myshop.dao.UserDao;
import myshop.dao.impl.UserDaoImpl;
import myshop.entity.User;
import myshop.service.UserService;
import myshop.utils.Constants;
import myshop.utils.EmailUtils;
import myshop.utils.MD5Utils;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/5 18:36
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean checkedUser(String username) throws SQLException {
        User user = userDao.selectUser(username);
        return user != null;
    }

    @Override
    public boolean registerUser(User user) throws SQLException {
        int res = userDao.insertUser(user);
        //发送一封邮件
        EmailUtils.sendEmail(user);
        return res==1;
    }

    @Override
    public int activeUser(String code) throws SQLException {
        User user = userDao.selectUserByCode(code);
        if(user==null){
            return Constants.ACTIVE_FAIL;
        }
        if(user.getStatus().equals(Constants.USER_ACTIVE)){
            return Constants.ACTIVE_ALREADY;
        }
        int res = userDao.updateStatusByUid(user.getUid());
        if(res==1){
            return Constants.ACTIVE_SUCCESS;
        }
        return Constants.ACTIVE_FAIL;
    }

    @Override
    public User loginUser(String username) throws SQLException {
        return userDao.selectUser(username);
    }

    @Override
    public List<User> findAllByState(int state) throws SQLException {
        return userDao.selectUserListByState(state);
    }

    @Override
    public boolean deleteUser(int uid) throws SQLException {
        return userDao.deleteUser(uid)==1;
    }

    @Override
    public User findUserByUserName(String username) throws SQLException {
        if("".equals(username)){
            return null;
        }
        return userDao.selectUser(username);

    }

    @Override
    public List<User> findAllByType(int state,String username, String sex) throws SQLException {
        if("".equals(username) && "".equals(sex)){
            return userDao.selectUserListByState(state);
        }
        return userDao.selectUserListByType(username,sex);
    }

}
