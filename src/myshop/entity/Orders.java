package myshop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zzx
 * 2020/12/5 16:36
 * 对应数据库的订单表
 */
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private String oid;
    private int uid;
    private User user;
    private int aid;
    private Address address;
    private BigDecimal count;
    //订单总金额

    private Date time;
    private int ostate;
    //订单状态 1 未付款，2已经付款未发货 3发货待收货 4 订单完成 -1 订单交易失败

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getOstate() {
        return ostate;
    }

    public void setOstate(int ostate) {
        this.ostate = ostate;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "oid='" + oid + '\'' +
                ", uid=" + uid +
                ", user=" + user +
                ", aid=" + aid +
                ", address=" + address +
                ", count=" + count +
                ", time=" + time +
                ", ostate=" + ostate +
                ", items=" + items +
                '}';
    }
}
