package myshop.dao;

import myshop.entity.Cart;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 18:00
 */
public interface CartDao {
    Cart hasProduct(String pid, int uid) throws SQLException, InvocationTargetException, IllegalAccessException;

    void updateCart(Cart cart) throws SQLException;

    void insertProduct(Cart cart) throws SQLException;

    List<Cart> selectCartByUid(int uid) throws SQLException, InvocationTargetException, IllegalAccessException;

    void deleteProduct(String cid) throws SQLException;

    void deleteCart(String uid) throws SQLException;

    void updateCartByCid(String cid,BigDecimal count,BigDecimal num) throws SQLException ;

    Cart selectProductByUid(int pid, int uid) throws SQLException, InvocationTargetException, IllegalAccessException;

    void deleteCartByCid(int cid) throws SQLException;
}
