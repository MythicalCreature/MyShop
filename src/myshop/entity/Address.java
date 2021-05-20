package myshop.entity;

import java.io.Serializable;

/**
 * @author zzx
 * 2020/12/5 16:04
 * 对应数据库的地址表
 */
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    private int aid;
    private int uid;
    private String name;
    private String phone;
    private String detail;
    private int astate = 0;
    //收件地址状态 0 非默认 1默认地址


    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getAstate() {
        return astate;
    }

    public void setAstate(int astate) {
        this.astate = astate;
    }

    @Override
    public String toString() {
        return "Address{" +
                "aid=" + aid +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", detail='" + detail + '\'' +
                ", astate=" + astate +
                '}';
    }
}