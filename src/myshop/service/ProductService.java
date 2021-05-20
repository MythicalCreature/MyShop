package myshop.service;

import myshop.entity.Page;
import myshop.entity.Product;
import myshop.entity.Type;

import java.sql.SQLException;

/**
 * @author zzx
 * 2020/12/6 16:00
 */
public interface ProductService {
    Page<Product> findPage(String tid, int currentPage, int pageSize) throws SQLException;

    Product findProductByPid(String pid) throws SQLException;

    Page<Product> findPageByType(String productName, String state,String productType, String time, int currentPage, int pageSize) throws SQLException;


    void addProduct(String name, String time, String state, String price, String productType, String image, String info) throws SQLException;

    Type findTypeByPid(String pid) throws SQLException;
}
