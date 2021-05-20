package myshop.dao;

import myshop.entity.Item;
import myshop.entity.Orders;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzx
 * 2020/12/7 9:43
 */
public interface OrdersDao {
    void insertOrders(Orders orders) throws SQLException ;

    void insertItems(List<Item> items) throws SQLException;

    List<Orders> selectOrdersByUid(int uid) throws SQLException, InvocationTargetException, IllegalAccessException;

    Orders selectOrdersByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException;

    List<Item> selectItemsByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException;

    void updateStateByOid(int state,String oid) throws SQLException;

    long selectCount() throws SQLException;

    List<Orders> selectOrdersByPage(int currentPage, int pageSize) throws SQLException;

    List<Orders> selectOrdersByPageAndType(int uid,String ostate, int currentPage, int pageSize) throws SQLException;

    long selectCountByType(int uid, String ostate) throws SQLException;
}
