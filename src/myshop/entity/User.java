package myshop.entity;

import java.io.Serializable;

/**
 * @author zzx
 * 2020/12/5 15:48
 * 对应数据库的用户表
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int uid;
    private String username;
    private String password;
    private String sex;
    private String status;
    //用户的激活状态 0 未激活 1 激活

    private String code;
    private String email;
    private int role;
    //用户 0 管理员 1

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
