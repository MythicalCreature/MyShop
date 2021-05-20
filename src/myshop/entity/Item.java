package myshop.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zzx
 * 2020/12/5 16:39
 * 对应数据库订单项
 */
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private int iid;
    private String oid;
    private int pid;
    private Product product;
    private BigDecimal count;
    private int num;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Item{" +
                "iid=" + iid +
                ", oid='" + oid + '\'' +
                ", pid=" + pid +
                ", count=" + count +
                ", num=" + num +
                '}';
    }
}

