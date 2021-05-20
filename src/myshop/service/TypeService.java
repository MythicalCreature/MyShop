package myshop.service;

import myshop.entity.Page;
import myshop.entity.Type;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 15:36
 */
public interface TypeService {
    List<Type> findAll() throws SQLException;

    Page<Type> findPage(int currentPage, int pageSize) throws SQLException;

    void addType(String typename,String typeInfo) throws SQLException;
}
