package myshop.service.impl;

import myshop.dao.ProductDao;
import myshop.dao.TypeDao;
import myshop.dao.impl.ProductDaoImpl;
import myshop.dao.impl.TypeDaoImpl;
import myshop.entity.*;
import myshop.service.ProductService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 16:01
 */
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao = new ProductDaoImpl();
    private TypeDao typeDao = new TypeDaoImpl();

    @Override
    public Page<Product> findPage(String tid, int currentPage, int pageSize) throws SQLException {
        long count;
        List<Product> productsList;
        if(tid==null){
            count = productDao.selectCount();
            productsList = productDao.selectProductByPage(currentPage,pageSize,"-1");
        }else{
            count = productDao.selectCountByTid(tid);
            productsList = productDao.selectProductByPage(currentPage,pageSize,tid);
        }
        for (Product product: productsList) {
            product.setType(typeDao.selectTypeByTid(product.getTid()));
        }
        return new Page<Product>(productsList,currentPage,pageSize,count);
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        return productDao.selectProductByPid(pid);
    }

    @Override
    public Page<Product> findPageByType(String productName, String state, String productType, String time,int currentPage, int pageSize) throws SQLException {
        List<Product>  productList;
        long count;
        if("".equals(productName) && "".equals(state)&&"".equals(productType)&&"".equals(time)){
            productList = productDao.selectProductByPage(currentPage,pageSize,"-1");
            count = productDao.selectCount();
        }else{
            productList = productDao.selectProductByPageAndType(productName,state,productType,time,currentPage,pageSize);
            count = productDao.selectCountByType(productName,state,productType,time);
        }
        for (Product product : productList) {
            product.setType(typeDao.selectTypeByTid(product.getTid()));
        }
        return new Page<Product>(productList,currentPage,pageSize,count);
    }

    @Override
    public void addProduct(String name, String time, String state, String price, String productType, String image, String info) throws SQLException {
        productDao.insertProduct(name,time,state,price,productType,image,info);
    }

    @Override
    public Type findTypeByPid(String pid) throws SQLException {
        int tid = productDao.findTidByPid(pid);
        return typeDao.selectTypeByTid(tid);
    }

}
