package myshop.dao;

import myshop.entity.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 16:00
 */
public interface ProductDao {
    long selectCountByTid(String tid) throws SQLException;

    List<Product> selectProductByPage(int currentPage, int pageSize,String tid) throws SQLException;

    Product selectProductByPid(String pid) throws SQLException;

    long selectCount() throws SQLException;

    List<Product> selectProductByPageAndType(String productName, String state,String productType, String time, int currentPage, int pageSize) throws SQLException;

    long selectCountByType(String productName, String state,String productType, String time) throws SQLException;


    void insertProduct(String name, String time, String state, String price, String productType, String image, String info) throws SQLException;

    int findTidByPid(String pid) throws SQLException;
}
