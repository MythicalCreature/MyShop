package myshop.controller;

import myshop.entity.Address;
import myshop.entity.Cart;
import myshop.entity.User;
import myshop.service.AddressService;
import myshop.service.impl.AddressServiceImpl;
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
 * 2020/12/6 20:23
 */
@WebServlet("/address")
public class AddressController extends BaseServlet {
    private AddressService addressService = new AddressServiceImpl();
    public String show(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, SQLException, InvocationTargetException {
        User user = (User)request.getSession().getAttribute("loginUser");
        int uid = user.getUid();
        List<Address> address = addressService.findAddressByUid(uid);
        request.setAttribute("list",address);
        return Constants.FORWARD+"/self_info.jsp";
    }
    public String defaultMethod (HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, SQLException, InvocationTargetException {
        String aid = request.getParameter("aid");
        User user = (User)request.getSession().getAttribute("loginUser");
        Address address = addressService.findDefaultAddress(user.getUid());
        int state = 0;
        if(address!=null){
            addressService.updateAddressStateByAid(state,address.getAid());
        }
        state = 1;
        addressService.updateAddressStateByAid(state,Integer.parseInt(aid));
        return Constants.FORWARD+"/address?method=show";
    }
    public String add (HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, SQLException, InvocationTargetException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
        int state = 0;
        User user = (User)request.getSession().getAttribute("loginUser");
        List<Address> addressList = addressService.findAddressByUid(user.getUid());
        if(addressList.size()==0){
            state = 1;
        }
        addressService.addAddress(name,phone,detail,state,user.getUid());
        return Constants.FORWARD+"/address?method=show";
    }
    public String update (HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, SQLException, InvocationTargetException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
        String aid = request.getParameter("aid");
        addressService.updateAddress(name,phone,detail,aid);
        return Constants.FORWARD+"/address?method=show";
    }
    public String delete (HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, SQLException, InvocationTargetException {
        String aid = request.getParameter("aid");
        addressService.deleteAddress(aid);
        return Constants.FORWARD+"/address?method=show";
    }
}
