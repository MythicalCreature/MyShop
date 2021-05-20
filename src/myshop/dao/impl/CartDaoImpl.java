package myshop.dao.impl;

import myshop.dao.CartDao;
import myshop.entity.Cart;
import myshop.entity.Product;
import myshop.utils.C3P0Utils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zzx
 * 2020/12/6 18:00
 */
public class CartDaoImpl implements CartDao {
    private QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public Cart hasProduct(String pid, int uid) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select  p.p_id as pid, p.t_id as tid, p.p_name as name, " +
                "p.p_time as time, p.p_image as image, p.p_state as state, p.p_info as info, " +
                "p.p_price as price, c.c_id as cid, c.u_id as uid, c.c_num as num, c.c_count as count " +
                "from product p join cart c on p.p_id=c.p_id where c.u_id=? and p.p_id=?";
        Map<String, Object> map = queryRunner.query(sql, new MapHandler(), uid, pid);
        if (map == null) {
            return null;
        }
        Cart cart = new Cart();
        Product product = new Product();
        BeanUtils.populate(cart, map);
        BeanUtils.populate(product, map);
        cart.setProduct(product);
        return cart;
    }

    @Override
    public void updateCart(Cart cart) throws SQLException {
        String sql = "update cart set c_num=?,c_count=? where c_id=?";
        queryRunner.update(sql, cart.getNum(), cart.getCount(), cart.getCid());
    }

    @Override
    public void insertProduct(Cart cart) throws SQLException {
        String sql = "insert into cart(u_id,p_id,c_num,c_count) values (?,?,?,?)";
        queryRunner.update(sql, cart.getUid(), cart.getPid(), cart.getNum(), cart.getCount());
    }

    @Override
    public List<Cart> selectCartByUid(int uid) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select  p.p_id as pid, p.t_id as tid, p.p_name as name, " +
                "p.p_time as time, p.p_image as image, p.p_state as state, p.p_info as info, " +
                "p.p_price as price, c.c_id as cid, c.u_id as uid, c.c_num as num, c.c_count as count " +
                "from product p join cart c on p.p_id=c.p_id where c.u_id=?";
        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), uid);
        if (list == null) {
            return null;
        }
        List<Cart> carts = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Cart cart = new Cart();
            Product product = new Product();
            BeanUtils.populate(cart, map);
            BeanUtils.populate(product, map);
            cart.setProduct(product);
            carts.add(cart);
        }
        return carts;
    }

    @Override
    public void deleteProduct(String cid) throws SQLException {
        String sql = "delete from cart where c_id=?";
        queryRunner.update(sql, cid);
    }

    @Override
    public void deleteCart(String uid) throws SQLException {
        String sql = "delete from cart where u_id=?";
        queryRunner.update(sql, uid);
    }

    @Override
    public void updateCartByCid(String cid, BigDecimal count, BigDecimal num) throws SQLException {
        String sql = "update cart set c_num=?,c_count=? where c_id=?";
        queryRunner.update(sql, num, count, cid);
    }

    @Override
    public Cart selectProductByUid(int pid, int uid) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select  p.p_id as pid, p.t_id as tid, p.p_name as name, " +
                "p.p_time as time, p.p_image as image, p.p_state as state, p.p_info as info, " +
                "p.p_price as price, c.c_id as cid, c.u_id as uid, c.c_num as num, c.c_count as count " +
                "from product p join cart c on p.p_id=c.p_id where p.p_id=? and c.u_id=?";
        Map<String, Object> map = queryRunner.query(sql, new MapHandler(), pid,uid);
        if (map == null) {
            return null;
        }
        Cart cart = new Cart();
        Product product = new Product();
        BeanUtils.populate(cart, map);
        BeanUtils.populate(product, map);
        cart.setProduct(product);
        return cart;
    }

    @Override
    public void deleteCartByCid(int cid) throws SQLException {
        String sql = "delete from cart where c_id=?";
        queryRunner.update(sql, cid);
    }
}
