package myshop.dao;

import myshop.entity.Type;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 15:38
 */
public interface TypeDao {
    List<Type> selectAll() throws SQLException;

    long selectCount() throws SQLException;

    List<Type> selectTypeByPage(int currentPage, int pageSize) throws SQLException;

    Type selectTypeByTid(int pid) throws SQLException;

    void insertType(String typename, String typeInfo) throws SQLException;
}
