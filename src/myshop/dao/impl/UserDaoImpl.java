package myshop.dao.impl;

import myshop.dao.UserDao;
import myshop.entity.Type;
import myshop.entity.User;
import myshop.utils.C3P0Utils;
import myshop.utils.Constants;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/5 18:45
 */
public class UserDaoImpl implements UserDao {
    private QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    @Override
    public User selectUser(String username) throws SQLException {
        String sql = "select u_id as uid, u_name as username,u_password as password," +
                "u_email as email, u_sex as sex, u_status as status, u_code as code," +
                "u_role as role from user where u_name=?";
        return queryRunner.query(sql,new BeanHandler<User>(User.class),username);
    }

    @Override
    public int insertUser(User user) throws SQLException {
        String sql = "insert into user (u_name,u_password,u_email, u_sex, u_status, u_code,u_role)" +
                " values (?,?,?,?,?,?,?)";
        return queryRunner.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getSex(),user.getStatus(),user.getCode(),user.getRole());
    }

    @Override
    public int updateStatusByUid(int uid) throws SQLException {
        String sql = "update user set u_status=? where u_id=?";
        return queryRunner.update(sql, Constants.USER_ACTIVE,uid);
    }

    @Override
    public User selectUserByCode(String code) throws SQLException {
        String sql = "select u_id as uid, u_name as username,u_password as password," +
                "u_email as email, u_sex as sex, u_status as status, u_code as code," +
                "u_role as role from user where u_code=?";
        return queryRunner.query(sql,new BeanHandler<User>(User.class),code);
    }

    @Override
    public List<User>  selectUserListByState(int state) throws SQLException {
        String sql = "select u_id as uid, u_name as username,u_password as password," +
                "u_email as email, u_sex as sex, u_status as status, u_code as code," +
                "u_role as role from user where u_status = ?";
        return queryRunner.query(sql,new BeanListHandler<User>(User.class),state);
    }

    @Override
    public int deleteUser(int uid) throws SQLException {
        String sql = "delete from user where u_id=?";
        return queryRunner.update(sql,uid);
    }

    @Override
    public User selectUserByUid(int uid) throws SQLException {
        String sql = "select u_id as uid, u_name as username,u_password as password," +
                "u_email as email, u_sex as sex, u_status as status, u_code as code," +
                "u_role as role from user where u_id=?";
        return queryRunner.query(sql,new BeanHandler<User>(User.class),uid);
    }

    @Override
    public List<User> selectUserListByType(String username, String sex) throws SQLException {
        StringBuilder sql = new StringBuilder("select u_id as uid, u_name as username,u_password as password," +
                "u_email as email, u_sex as sex, u_status as status, u_code as code," +
                "u_role as role from user ");
        if(!("".equals(username) )&& !("".equals(sex))){
            sql.append("where u_name = '");
            sql.append(username);
            sql.append("' and u_sex = '");
            sql.append(sex);
            sql.append("'");
        }else{
            if(!("".equals(username) )){
                sql.append("where u_name = '");
                sql.append(username);
                sql.append("'");
            }else{
                sql.append("where u_sex = '");
                sql.append(sex);
                sql.append("'");
            }
        }
        return queryRunner.query(sql.toString(),new BeanListHandler<User>(User.class));
    }
}
