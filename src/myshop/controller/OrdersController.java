package myshop.controller;
import com.google.gson.Gson;
import myshop.entity.*;
import myshop.service.AddressService;
import myshop.service.CartService;
import myshop.service.OrdersService;
import myshop.service.UserService;
import myshop.service.impl.AddressServiceImpl;
import myshop.service.impl.CartServiceImpl;
import myshop.service.impl.OrdersServiceImpl;
import myshop.service.impl.UserServiceImpl;
import myshop.utils.Constants;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzx
 * 2020/12/7 9:37
 */
@WebServlet("/orders")
public class OrdersController extends BaseServlet {
    private OrdersService ordersService = new OrdersServiceImpl();
    private AddressService addressService = new AddressServiceImpl();
    private CartService cartService = new CartServiceImpl();
    private UserService userService = new UserServiceImpl();

    public String preView(HttpServletRequest request, HttpServletResponse response) throws SQLException, InvocationTargetException, IllegalAccessException {
        String uid = request.getParameter("uid");
        List<Address> addressList = addressService.findAddressByUid(Integer.parseInt(uid));
        //已选择
        String[] productsId = request.getParameterValues("product");
        List<Cart> cartList = new ArrayList<>();
        for (String pid : productsId) {
            Cart cart = cartService.findProductByUid(Integer.parseInt(pid),Integer.parseInt(uid));
            cartList.add(cart);
        }
        request.setAttribute("addressList",addressList);
        request.getSession().setAttribute("cartList",cartList);
        request.getSession().setAttribute("flag",false);
        return Constants.FORWARD+"/order.jsp";
    }
    public String create(HttpServletRequest request, HttpServletResponse response) throws SQLException, InvocationTargetException, IllegalAccessException {
        String uid = request.getParameter("uid");
        String aid = request.getParameter("aid");
        String sum = request.getParameter("sum");
        List<Cart>  cartList = (List<Cart>) request.getSession().getAttribute("cartList");
        Boolean flag =(Boolean) request.getSession().getAttribute("flag");
        String oid = ordersService.createOrders(uid,aid,sum,cartList,flag);
        request.setAttribute("oid",oid);
        return Constants.FORWARD+"/orders?method=detail";
    }
    public String show(HttpServletRequest request, HttpServletResponse response) throws SQLException, InvocationTargetException, IllegalAccessException {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("loginUser");
        if(user==null){
            request.setAttribute("msg","登录后才可以查看订单");
            return Constants.FORWARD+"/login.jsp";
        }
        List<Orders> ordersList = ordersService.findOrdersByUid(((User)user).getUid());
        request.setAttribute("ordersList",ordersList);
        return Constants.FORWARD+"/orderList.jsp";
    }
    public String detail(HttpServletRequest request, HttpServletResponse response) throws SQLException, InvocationTargetException, IllegalAccessException {
        String oid = request.getParameter("oid");
        if(oid==null){
            oid = (String)request.getAttribute("oid");
        }
        Orders orders = ordersService.findOrdersByOid(oid);
        request.setAttribute("orders",orders);
        return Constants.FORWARD+"/orderDetail.jsp";
    }
    public String success(HttpServletRequest request, HttpServletResponse response) throws SQLException, InvocationTargetException, IllegalAccessException {
        String result = request.getParameter("result");
        String oid = request.getParameter("oid");
        int state = 2;
        if ("SUCCESS".equals(result)) {
            ordersService.updateStateByOid(state,oid);
            return Constants.FORWARD+"/orders?method=show";
        }else{
            state = -1;
            ordersService.updateStateByOid(state,oid);
            request.setAttribute("msg","订单"+oid+"支付失败");
            return Constants.FORWARD+"/message.jsp";
        }
    }
    public String getOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, InvocationTargetException, IllegalAccessException {
        String oid = request.getParameter("oid");
        int state = 4;
        ordersService.updateStateByOid(state,oid);
        return Constants.FORWARD+"/orders?method=show";

    }
    public String payDirect(HttpServletRequest request, HttpServletResponse response) throws SQLException, InvocationTargetException, IllegalAccessException {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("loginUser");
        if(user==null){
            request.setAttribute("msg","登录后才可以购买商品");
            return Constants.FORWARD+"/login.jsp";
        }
        String pid = request.getParameter("pid");
        List<Address> addressList = addressService.findAddressByUid(((User)user).getUid());
        boolean flag = true;
        Cart cart = cartService.findProductByUid(Integer.parseInt(pid),((User)user).getUid());
        if(cart==null){
            flag = false;
            cartService.addProduct(((User)user).getUid(),pid);
            cart = cartService.findProductByUid(Integer.parseInt(pid),((User)user).getUid());
        }
        cart.setNum(1);
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        request.getSession().setAttribute("flag",flag);
        request.setAttribute("addressList",addressList);
        request.getSession().setAttribute("cartList",cartList);
        return Constants.FORWARD+"/order.jsp";
    }
    public String getAllOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException{
        int pageSize = 4;
        String cPage = request.getParameter("currentPage");
        int currentPage = 1;
        if(cPage!=null){
            currentPage = Integer.parseInt(cPage);
        }
        Page<Orders> pageBean = ordersService.findPage(currentPage,pageSize);
        request.setAttribute("pageBean",pageBean);
        return Constants.FORWARD+"/admin/showAllOrder.jsp";
    }
    public String sendOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException{
        String oid = request.getParameter("oid");
        int state = 3;
        ordersService.updateStateByOid(state,oid);
        return Constants.FORWARD+"/orders?method=getAllOrder";
    }
    public String searchOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException{
        int pageSize = 4;
        String cPage = request.getParameter("currentPage");
        int currentPage = 1;
        if(cPage!=null){
            currentPage = Integer.parseInt(cPage);
        }
        String username = request.getParameter("username");
        String ostate = request.getParameter("ostate");
        Page<Orders> pageBean = ordersService.findPageByType(username,ostate,currentPage,pageSize);
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("username",username);
        request.setAttribute("ostate",ostate);
        return Constants.FORWARD+"/admin/showAllOrder.jsp";
    }
}
