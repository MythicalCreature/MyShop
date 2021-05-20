package myshop.service.impl;

import myshop.dao.CartDao;
import myshop.dao.OrdersDao;
import myshop.dao.UserDao;
import myshop.dao.impl.CartDaoImpl;
import myshop.dao.impl.OrdersDaoImpl;
import myshop.dao.impl.UserDaoImpl;
import myshop.entity.*;
import myshop.service.OrdersService;
import myshop.utils.RandomUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zzx
 * 2020/12/7 9:43
 */
public class OrdersServiceImpl implements OrdersService {
    private OrdersDao ordersDao = new OrdersDaoImpl();
    private CartDao cartDao = new CartDaoImpl();
    private UserDao userDao = new UserDaoImpl();

    @Override
    public String createOrders(String uid, String aid, String sum ,List<Cart> cartList,Boolean flag) throws IllegalAccessException, SQLException, InvocationTargetException {
        Orders orders = new Orders();
        orders.setAid(Integer.parseInt(aid));
        orders.setCount(new BigDecimal(sum));
        orders.setUid(Integer.parseInt(uid));
        String orderId = RandomUtils.createOrderId();
        orders.setOid(orderId);
        orders.setTime(new Date());
        orders.setOstate(1);
        ordersDao.insertOrders(orders);
        List<Item> items = new ArrayList<>();
        for (Cart cart : cartList) {
            Item item = new Item();
            item.setOid(orderId);
            item.setCount(cart.getCount());
            item.setNum(cart.getNum());
            item.setPid(cart.getPid());
            items.add(item);
        }
        ordersDao.insertItems(items);
        if(!flag){
            for (Cart cart : cartList) {
                cartDao.deleteCartByCid(cart.getCid());
            }
        }
        return orderId;
    }

    @Override
    public List<Orders> findOrdersByUid(int uid) throws IllegalAccessException, SQLException, InvocationTargetException {
        return ordersDao.selectOrdersByUid(uid);
    }

    @Override
    public Orders findOrdersByOid(String oid) throws IllegalAccessException, SQLException, InvocationTargetException {
        Orders orders = ordersDao.selectOrdersByOid(oid);
        List<Item> items = ordersDao.selectItemsByOid(oid);
        orders.setItems(items);
        return orders;
    }

    @Override
    public void updateStateByOid(int state,String oid) throws SQLException {
        ordersDao.updateStateByOid(state,oid);
    }

    @Override
    public Page<Orders> findPage(int currentPage, int pageSize) throws SQLException {
        long count = ordersDao.selectCount();
        List<Orders>  ordersList = ordersDao.selectOrdersByPage(currentPage,pageSize);
        for (Orders orders : ordersList) {
            orders.setUser(userDao.selectUserByUid(orders.getUid()));
        }
        return new Page<Orders>(ordersList,currentPage,pageSize,count);
    }

    @Override
    public Page<Orders> findPageByType(String username,String ostate,int currentPage, int pageSize) throws SQLException {
        List<Orders>  ordersList;
        long count;
        if("".equals(username) && "".equals(ostate)){
            ordersList = ordersDao.selectOrdersByPage(currentPage,pageSize);
            count = ordersDao.selectCount();
        }else{
            User user = userDao.selectUser(username);
            if(user==null){
                ordersList = ordersDao.selectOrdersByPageAndType(-1,ostate,currentPage,pageSize);
                count = ordersDao.selectCountByType(-1,ostate);

            }else{
                ordersList = ordersDao.selectOrdersByPageAndType(user.getUid(),ostate,currentPage,pageSize);
                count = ordersDao.selectCountByType(user.getUid(),ostate);

            }
        }
        for (Orders orders : ordersList) {
            orders.setUser(userDao.selectUserByUid(orders.getUid()));
        }
        return new Page<Orders>(ordersList,currentPage,pageSize,count);
    }

}
