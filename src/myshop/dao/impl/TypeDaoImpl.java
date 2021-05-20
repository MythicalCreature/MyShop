package myshop.dao.impl;

import myshop.dao.TypeDao;
import myshop.entity.Orders;
import myshop.entity.Type;
import myshop.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 15:39
 */
public class TypeDaoImpl implements TypeDao {
    private QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    @Override
    public List<Type> selectAll() throws SQLException {
        String sql = "select t_id as tid, t_name as name, t_info as info from type limit 5;";
        return queryRunner.query(sql,new BeanListHandler<Type>(Type.class));
    }

    @Override
    public long selectCount() throws SQLException {
        String sql = "select count(1) from type";
        return (long) queryRunner.query(sql, new ScalarHandler());
    }

    @Override
    public List<Type> selectTypeByPage(int currentPage, int pageSize) throws SQLException {
        String sql = "select  t_id as tid, t_name as name, t_info as info from type order by t_id asc limit ?,?";
        return queryRunner.query(sql, new BeanListHandler<Type>(Type.class), pageSize * (currentPage - 1), pageSize);
    }

    @Override
    public Type selectTypeByTid(int tid) throws SQLException {
        String sql = "select t_id as tid, t_name as name, t_info as info from type where t_id = ?;";
        return queryRunner.query(sql,new BeanHandler<Type>(Type.class),tid);
    }

    @Override
    public void insertType(String typename, String typeInfo) throws SQLException {
        String sql = "insert into type (t_name,t_info) values(?,?)";
        queryRunner.update(sql,typename,typeInfo);
    }
}
