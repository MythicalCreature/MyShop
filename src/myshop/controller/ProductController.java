package myshop.controller;

import myshop.entity.Orders;
import myshop.entity.Page;
import myshop.entity.Product;
import myshop.entity.Type;
import myshop.service.ProductService;
import myshop.service.TypeService;
import myshop.service.impl.ProductServiceImpl;
import myshop.service.impl.TypeServiceImpl;
import myshop.utils.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 15:58
 */
@WebServlet("/product")
public class ProductController extends BaseServlet {
    private ProductService productService = new ProductServiceImpl();
    private TypeService typeService = new TypeServiceImpl();

    public String show (HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String tid = request.getParameter("tid");
        int pageSize = 4;
        String cPage = request.getParameter("currentPage");
        int currentPage = 1;
        if(cPage!=null){
            currentPage = Integer.parseInt(cPage);
        }
        Page<Product> pageBean = productService.findPage(tid,currentPage,pageSize);
        request.setAttribute("pageBean",pageBean);
        return Constants.FORWARD+"/goodsList.jsp";
    }
    public String detail (HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String pid = request.getParameter("pid");
        Product product = productService.findProductByPid(pid);
        Type type = productService.findTypeByPid(pid);
        request.setAttribute("type",type);
        request.setAttribute("product",product);
        return Constants.FORWARD+"/goodsDetail.jsp";
    }

    public String getProductType(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int pageSize = 4;
        String cPage = request.getParameter("currentPage");
        int currentPage = 1;
        if(cPage!=null){
            currentPage = Integer.parseInt(cPage);
        }
        Page<Product> pageBean = productService.findPage(null,currentPage,pageSize);
        List<Type> typeList = typeService.findAll();
        HttpSession session = request.getSession();
        session.setAttribute("typeList",typeList);
        request.setAttribute("pageBean",pageBean);
        return Constants.FORWARD+"/admin/showGoods.jsp";
    }
    public String searchProductType(HttpServletRequest request, HttpServletResponse response) throws SQLException{
        int pageSize = 4;
        String cPage = request.getParameter("currentPage");
        int currentPage = 1;
        if(cPage!=null){
            currentPage = Integer.parseInt(cPage);
        }
        String productName = request.getParameter("productName");
        String state = request.getParameter("state");
        String productType = request.getParameter("productType");
        String time = request.getParameter("time");
        Page<Product> pageBean = productService.findPageByType(productName,state,productType,time,currentPage,pageSize);
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("productName",productName);
        request.setAttribute("state",state);
        request.setAttribute("productType",productType);
        request.setAttribute("time",time);
        return Constants.FORWARD+"/admin/showGoods.jsp";
    }
    public String add(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String name = request.getParameter("name");
        String time = request.getParameter("time");
        String state = request.getParameter("state");
        String price = request.getParameter("price");
        String productType = request.getParameter("productType");
        String image = request.getParameter("image");
        String info = request.getParameter("info");
        productService.addProduct(name,time,state,price,productType,image,info);
        return "商品添加成功";
    }
}
