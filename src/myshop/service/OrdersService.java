package myshop.service;

import myshop.entity.Cart;
import myshop.entity.Orders;
import myshop.entity.Page;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/7 9:42
 */
public interface OrdersService {
    String createOrders(String uid, String aid, String sum, List<Cart> cartList,Boolean flag) throws IllegalAccessException, SQLException, InvocationTargetException;

    List<Orders> findOrdersByUid(int uid) throws IllegalAccessException, SQLException, InvocationTargetException;

    Orders findOrdersByOid(String oid) throws IllegalAccessException, SQLException, InvocationTargetException;

    void updateStateByOid(int state,String oid) throws SQLException;

    Page<Orders> findPage(int currentPage, int pageSize) throws SQLException;

    Page<Orders> findPageByType(String username,String ostate, int currentPage, int pageSize) throws SQLException;
}
