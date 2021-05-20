package myshop.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zzx
 * 2020/12/5 16:08
 * 数据库对应的购物车表
 */

public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    private int cid;
    private int uid;
    private int pid;
    private Product product;
    private int num = 0;
    //购车商品数量

    private BigDecimal count;
    //购物车小计

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public BigDecimal getCount() {
        BigDecimal price = this.product.getPrice();
        return new BigDecimal(num).multiply(price);
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", num=" + num +
                ", count=" + count +
                '}';
    }
}
