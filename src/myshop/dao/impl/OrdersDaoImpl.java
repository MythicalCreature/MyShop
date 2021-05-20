package myshop.dao.impl;

import myshop.dao.OrdersDao;
import myshop.entity.*;
import myshop.utils.C3P0Utils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zzx
 * 2020/12/7 9:43
 */
public class OrdersDaoImpl implements OrdersDao {
    private QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public void insertOrders(Orders orders) throws SQLException {
        String sql = "insert into orders(o_id,a_id,u_id,o_count,o_state,o_time) values (?,?,?,?,?,?)";
        queryRunner.update(sql, orders.getOid(), orders.getAid(), orders.getUid(), orders.getCount(), orders.getOstate(), orders.getTime());
    }

    @Override
    public void insertItems(List<Item> items) throws SQLException {
        Object[][] obj = new Object[items.size()][];
        String sql = "insert into item(o_id,p_id,i_count,i_num) values(?,?,?,?)";
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            obj[i] = new Object[]{item.getOid(), item.getPid(), item.getCount(), item.getNum()};
        }
        queryRunner.batch(sql, obj);
    }

    @Override
    public List<Orders> selectOrdersByUid(int uid) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select o.o_id as oid, o.u_id as uid, o.a_id as aid, o.o_count as count, o.o_time as time, " +
                "o.o_state as ostate, a.a_name as name, a.a_phone as phone, a.a_detail as detail, a.a_state as astate " +
                "from address a join orders o on a.a_id=o.a_id where o.u_id=? order by o.o_id desc";
        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), uid);
        if (list == null) {
            return null;
        }
        ArrayList<Orders> ordersArrayList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Orders orders = new Orders();
            Address address = new Address();
            BeanUtils.populate(orders, map);
            BeanUtils.populate(address, map);
            orders.setAddress(address);
            ordersArrayList.add(orders);
        }
        return ordersArrayList;
    }

    @Override
    public Orders selectOrdersByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select o.o_id as oid, o.u_id as uid, o.a_id as aid, o.o_count as count, o.o_time as time, " +
                "o.o_state as ostate, a.a_name as name, a.a_phone as phone, a.a_detail as detail, a.a_state as astate " +
                "from address a join orders o on a.a_id=o.a_id where o.o_id=?";
        Map<String, Object> map = queryRunner.query(sql, new MapHandler(), oid);
        if (map == null) {
            return null;
        }
        Orders orders = new Orders();
        Address address = new Address();
        BeanUtils.populate(orders, map);
        BeanUtils.populate(address, map);
        orders.setAddress(address);
        return orders;
    }

    @Override
    public List<Item> selectItemsByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select p.p_id as pid, p.t_id as tid, p.p_name as name, p.p_time as time, p.p_image as image, " +
                "p.p_state as state, p.p_info as info, p.p_price as price, i.i_id as iid, i.o_id as oid, " +
                "i.i_count as count, i.i_num as num from product p join item i on p.p_id=i.p_id where i.o_id=?";
        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), oid);
        if (list == null) {
            return null;
        }
        List<Item> itemArrayList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Item item = new Item();
            Product product = new Product();
            BeanUtils.populate(item, map);
            BeanUtils.populate(product, map);
            item.setProduct(product);
            itemArrayList.add(item);
        }
        return itemArrayList;
    }

    @Override
    public void updateStateByOid(int state, String oid) throws SQLException {
        String sql = "update orders set o_state=? where o_id=?";
        queryRunner.update(sql, state, oid);
    }

    @Override
    public long selectCount() throws SQLException {
        String sql = "select count(1) from orders";
        return (long) queryRunner.query(sql, new ScalarHandler());
    }

    @Override
    public List<Orders> selectOrdersByPage(int currentPage, int pageSize) throws SQLException {
        String sql = "select o_id as oid, u_id as uid, a_id as aid, o_count as count," +
                " o_time as time, o_state as ostate from orders order by o_id desc limit ?,?";
        return queryRunner.query(sql, new BeanListHandler<Orders>(Orders.class), pageSize * (currentPage - 1), pageSize);
    }

    @Override
    public List<Orders> selectOrdersByPageAndType(int uid, String ostate, int currentPage, int pageSize) throws SQLException {
        StringBuilder sql = new StringBuilder("select o_id as oid, u_id as uid, a_id as aid, o_count as count, " +
                "o_time as time, o_state as ostate from orders where ");
        if (uid != -1) {
            sql.append(" u_id= '");
            sql.append(uid);
            sql.append("' ");
        }
        if (uid != -1 && !("".equals(ostate))) {
            sql.append(" and ");
        }
        if (!("".equals(ostate))) {
            sql.append(" o_state = '");
            sql.append(Integer.parseInt(ostate));
            sql.append("'");
        }
        sql.append("order by o_id desc limit ?,?");
        return queryRunner.query(sql.toString(), new BeanListHandler<Orders>(Orders.class), pageSize * (currentPage - 1), pageSize);
    }

    @Override
    public long selectCountByType(int uid, String ostate) throws SQLException {
        StringBuilder sql = new StringBuilder("select count(1) from orders where ");
        if(uid!=-1){
            sql.append(" u_id= '");
            sql.append(uid);
            sql.append("' ");
        }
        if(uid!=-1&&!("".equals(ostate))){
            sql.append(" and ");
        }
        if(!("".equals(ostate))){
            sql.append(" o_state = '");
            sql.append(Integer.parseInt(ostate));
            sql.append("'");
        }
        return (long) queryRunner.query(sql.toString(), new ScalarHandler());

    }
}
