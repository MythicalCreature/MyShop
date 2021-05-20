package myshop.controller;

import myshop.entity.Cart;
import myshop.entity.User;
import myshop.service.CartService;
import myshop.service.impl.CartServiceImpl;
import myshop.utils.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 17:47
 */
@WebServlet("/cart")
public class CartController extends BaseServlet {
    private CartService cartService = new CartServiceImpl();
    public String add(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, SQLException, InvocationTargetException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("loginUser");
        if (user != null) {
            String pid = request.getParameter("pid");
            int uid = user.getUid();
            cartService.addProduct(uid,pid);
            return Constants.FORWARD+"/cartSuccess.jsp";
        }else{
            request.setAttribute("msg","请先登录");
            return Constants.FORWARD+"/login.jsp";
        }
    }
    public String update(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, SQLException, InvocationTargetException {
        String cid = request.getParameter("cid");
        String num = request.getParameter("num");
        String price = request.getParameter("price");
        cartService.updateCart(cid,num,price);
        return Constants.FORWARD+"/cart?method=show";
    }
    public String delete(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, SQLException, InvocationTargetException {
            String cid = request.getParameter("cid");
            cartService.deleteProduct(cid);
            return Constants.FORWARD+"/cart?method=show";
    }
    public String clear(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, SQLException, InvocationTargetException {
        String uid = request.getParameter("uid");
        cartService.deleteCart(uid);
        return Constants.FORWARD+"/cart?method=show";
    }
    public String show(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, SQLException, InvocationTargetException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("loginUser");
        if (user != null) {
            int uid = user.getUid();
            List<Cart> carts = cartService.findAll(uid);
            request.setAttribute("list",carts);
            return Constants.FORWARD+"/cart.jsp";
        }else{
            request.setAttribute("msg","请先登录");
            return Constants.FORWARD+"/login.jsp";
        }
    }
}
