package myshop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zzx
 * 2020/12/5 16:09
 * 对应数据库的商品表
 */
public class Product implements Serializable {

    private static  final long serialVersionUID = 1L;

    private int pid;
    private int tid;
    private Type type;
    private String name;
    private Date time;
    //商品的上架时间！ 数据库date --> java.util.Date

    private String image;
    //商品的图片名称

    private int state;
    //商品的热门指数

    private String info;
    private BigDecimal price;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", tid=" + tid +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", image='" + image + '\'' +
                ", state=" + state +
                ", info='" + info + '\'' +
                ", price=" + price +
                '}';
    }
}

