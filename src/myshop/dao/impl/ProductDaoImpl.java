package myshop.dao.impl;

import myshop.dao.ProductDao;
import myshop.entity.Orders;
import myshop.entity.Product;
import myshop.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 15:59
 */
public class ProductDaoImpl implements ProductDao {
    private QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    @Override
    public long selectCountByTid(String tid) throws SQLException {
        String sql = "select count(1) from product where t_id=?";
        return (long)queryRunner.query(sql,new ScalarHandler(),tid);
    }

    @Override
    public List<Product> selectProductByPage(int currentPage, int pageSize,String tid) throws SQLException {
        StringBuilder sql = new StringBuilder("select p_id as pid, t_id as tid, p_name as name, p_time as time, p_image as image, " +
                "p_state as state, p_info as info, p_price as price from product ");
        if (!("-1".equals(tid))) {
            sql.append(" where t_id= '");
            sql.append(tid);
            sql.append("' ");
        }
        sql.append("order by p_id asc limit ?,?");
        return queryRunner.query(sql.toString(),new BeanListHandler<Product>(Product.class),pageSize*(currentPage-1),pageSize);
    }

    @Override
    public Product selectProductByPid(String pid) throws SQLException {
        String sql = "select p_id as pid, t_id as tid, p_name as name, p_time as time, p_image as image, " +
                "p_state as state, p_info as info, p_price as price from product where p_id=?";
        return queryRunner.query(sql,new BeanHandler<Product>(Product.class),pid);
    }

    @Override
    public long selectCount() throws SQLException {
        String sql = "select count(1) from product ";
        return (long)queryRunner.query(sql,new ScalarHandler());
    }

    @Override
    public List<Product> selectProductByPageAndType(String productName, String state, String productType, String time, int currentPage, int pageSize) throws SQLException {
        StringBuilder sql = new StringBuilder("select p_id as pid, t_id as tid, p_name as name, p_time as time, p_image as image, " +
                "p_state as state, p_info as info, p_price as price from product where ");
        if (!("".equals(productName))) {
            sql.append(" p_name= '");
            sql.append(productName);
            sql.append("' ");
        }
        if (!("".equals(productName))&& !("".equals(state))) {
            sql.append(" and ");
        }
        if (!("".equals(state))) {
            sql.append(" p_state = '");
            sql.append(Integer.parseInt(state));
            sql.append("'");
        }
        if (!("".equals(productType))&& !("".equals(state))) {
            sql.append(" and ");
        }
        if (!("".equals(productType))) {
            sql.append(" t_id= '");
            sql.append(Integer.parseInt(productType));
            sql.append("' ");
        }
        if (!("".equals(productType))&& !("".equals(time))) {
            sql.append(" and ");
        }
        if (!("".equals(time))) {
            sql.append(" p_time = '");
            sql.append(time);
            sql.append("'");
        }
        sql.append("order by p_id asc limit ?,?");
        return queryRunner.query(sql.toString(), new BeanListHandler<Product>(Product.class), pageSize * (currentPage - 1), pageSize);
    }

    @Override
    public long selectCountByType(String productName, String state,String productType, String time) throws SQLException {
        StringBuilder sql = new StringBuilder("select count(1) from product where ");
        if(!("".equals(productName))){
            sql.append(" p_name= '");
            sql.append(productName);
            sql.append("' ");
        }
        if(!("".equals(productName))&&!("".equals(state))){
            sql.append(" and ");
        }
        if(!("".equals(state))){
            sql.append(" p_state = '");
            sql.append(Integer.parseInt(state));
            sql.append("'");
        }
        if(!("".equals(productType))){
            sql.append(" t_id= '");
            sql.append(productType);
            sql.append("' ");
        }
        if(!("".equals(productType))&&!("".equals(time))){
            sql.append(" and ");
        }
        if(!("".equals(time))){
            sql.append(" p_time = '");
            sql.append(new Date(time));
            sql.append("'");
        }
        return (long) queryRunner.query(sql.toString(), new ScalarHandler());
    }

    @Override
    public void insertProduct(String name, String time, String state, String price, String productType, String image, String info) throws SQLException {
        String sql = "insert into product (p_name,p_time,p_state,p_price,p_image,t_id,p_info) values(?,?,?,?,?,?,?)";
        queryRunner.update(sql,name,time,Integer.parseInt(state),new BigDecimal(price),"image/"+image,Integer.parseInt(productType),info);
    }

    @Override
    public int findTidByPid(String pid) throws SQLException {
        String sql = "select t_id as tid from product where p_id=? ";
        return (Integer) queryRunner.query(sql, new ScalarHandler(), pid);
    }


}
