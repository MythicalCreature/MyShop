package myshop.service.impl;

import myshop.dao.CartDao;
import myshop.dao.ProductDao;
import myshop.dao.impl.CartDaoImpl;
import myshop.dao.impl.ProductDaoImpl;
import myshop.entity.Cart;
import myshop.entity.Product;
import myshop.service.CartService;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 17:51
 */
public class CartServiceImpl implements CartService {
    private CartDao cartDao = new CartDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    @Override
    public void addProduct(int uid, String pid) throws SQLException, InvocationTargetException, IllegalAccessException {
        Cart cart = cartDao.hasProduct(pid,uid);
        if (cart != null) {
            cart.setNum(cart.getNum()+1);
            cartDao.updateCart(cart);
        }else{
            Product product = productDao.selectProductByPid(pid);
            cart = new Cart();
            cart.setUid(uid);
            cart.setNum(1);
            cart.setPid(Integer.parseInt(pid));
            cart.setProduct(product);
            cartDao.insertProduct(cart);
        }

    }

    @Override
    public List<Cart> findAll(int uid) throws IllegalAccessException, SQLException, InvocationTargetException {
        return cartDao.selectCartByUid(uid);
    }

    @Override
    public void deleteProduct(String cid) throws SQLException {
        cartDao.deleteProduct(cid);
    }

    @Override
    public void deleteCart(String uid) throws SQLException {
        cartDao.deleteCart(uid);
    }

    @Override
    public void updateCart(String cid, String num, String price) throws SQLException {
        BigDecimal bigDecimalNum = new BigDecimal(num);
        BigDecimal bigDecimalPrice = new BigDecimal(price);
        BigDecimal count = bigDecimalNum.multiply(bigDecimalPrice);
        cartDao.updateCartByCid(cid,count,bigDecimalNum);
    }

    @Override
    public Cart findProductByUid(int pid,int uid) throws IllegalAccessException, SQLException, InvocationTargetException {
        return cartDao.selectProductByUid(pid,uid);
    }
}
