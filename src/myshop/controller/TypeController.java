package myshop.controller;

import com.google.gson.Gson;
import myshop.entity.Orders;
import myshop.entity.Page;
import myshop.entity.Type;
import myshop.service.TypeService;
import myshop.service.impl.TypeServiceImpl;
import myshop.utils.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 15:33
 */
@WebServlet("/type")
public class TypeController extends BaseServlet {
    private TypeService typeService = new TypeServiceImpl();
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        List<Type> list =  typeService.findAll();
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public String add(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String typename = request.getParameter("typename");
        String typeInfo = request.getParameter("typeInfo");
        typeService.addType(typename,typeInfo);
        return "商品种类添加成功";
    }
    public String getTypeList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        List<Type> list =  typeService.findAll();
        HttpSession session = request.getSession();
        session.setAttribute("typeList",list);
        return Constants.FORWARD+"/admin/addGoods.jsp";
    }

}
