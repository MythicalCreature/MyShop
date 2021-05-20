package myshop.service;

import myshop.entity.Cart;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 17:51
 */
public interface CartService {
    void addProduct(int uid, String pid) throws SQLException, InvocationTargetException, IllegalAccessException;

    List<Cart> findAll(int uid) throws IllegalAccessException, SQLException, InvocationTargetException;

    void deleteProduct(String cid) throws SQLException;

    void deleteCart(String uid) throws SQLException;

    void updateCart(String cid, String num, String price) throws SQLException;

    Cart findProductByUid(int pid,int uid) throws IllegalAccessException, SQLException, InvocationTargetException;
}
