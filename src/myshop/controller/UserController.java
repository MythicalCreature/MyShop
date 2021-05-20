package myshop.controller;

import com.google.gson.Gson;
import myshop.entity.User;
import myshop.service.UserService;
import myshop.service.impl.UserServiceImpl;
import myshop.utils.Base64Utils;
import myshop.utils.Constants;
import myshop.utils.MD5Utils;
import myshop.utils.RandomUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author zzx
 * 2020/12/5 18:34
 */
@WebServlet("/user")
public class UserController extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    /**
     * 检查用户名是否存在
     */
    public String checkUserName(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        //1.获取用户名
        String username = request.getParameter("username");
        if (username == null) {
            return Constants.HAS_USER;
        }
        //2.调用业务逻辑判断用户名是否存在
        boolean b = userService.checkedUser(username);
        //3.响应字符串 1：表示存在 0：表示不存在
        if(b){
            return Constants.HAS_USER;
        }
        return Constants.NOT_HAS_USER;
    }

    public String register(HttpServletRequest request, HttpServletResponse response)  {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setCode(RandomUtils.createActive());
        user.setRole(Constants.ROLE_CUSTOMER);
        user.setStatus(Constants.USER_NOT_ACTIVE);
        user.setPassword(MD5Utils.md5(user.getPassword()));
        try {
            userService.registerUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("registerMsg","注册失败");
            return Constants.FORWARD+"/register.jsp";
        }
        return Constants.FORWARD+"/registerSuccess.jsp";
    }

    public String active(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String c = request.getParameter("c");
        String code = Base64Utils.decode(c);
        int res = userService.activeUser(code);
        if(res==0){
            request.setAttribute("msg","未激活成功");
        }else if(res==1){
            request.setAttribute("msg","激活成功，请登录");
        }else{
            request.setAttribute("msg","已经激活");
        }
        return Constants.FORWARD+"/message.jsp";
    }

    public String getUserList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String invalid = request.getParameter("invalid");
        int state = 1;
        if("true".equals(invalid)){
            state = 0;
        }
        List<User> userList = userService.findAllByState(state);
        Gson gson = new Gson();
        return gson.toJson(userList);
    }
    public String searchUser(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String username = request.getParameter("username");
        String sex = request.getParameter("sex");
        int state = 1;
        List<User> userList = userService.findAllByType(state,username,sex);
        Gson gson = new Gson();
        return gson.toJson(userList);
    }
    public String logOut(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        session.setAttribute("loginUser",null);
        Cookie cookie = new Cookie(Constants.AUTO_NAME,"");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return Constants.REDIRECT+"/index.jsp";
    }
    public String login(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String admin = request.getParameter("admin");
        HttpSession session = request.getSession();
        if("true".equals(admin)){
            User user = userService.loginUser(username);
            if(user!=null) {
                if (user.getPassword().equalsIgnoreCase(password)) {
                    if (user.getRole() == 1) {
                        session.setAttribute("admin", user);
                        return Constants.REDIRECT + "/admin/admin.jsp";
                    }
                }
            }
            request.setAttribute("msg","用户名或密码错误");
            return Constants.FORWARD+"/admin/login.jsp";
        }
        String code = request.getParameter("code");
        String auto = request.getParameter("auto");
        String trueCode = (String)session.getAttribute("code");
        if(!(trueCode.equalsIgnoreCase(code))){
            request.setAttribute("msg","验证码错误");
           return Constants.FORWARD+"/login.jsp";
        }
        User user = userService.loginUser(username);
        String md5password = MD5Utils.md5(password);
        if(user==null||!(user.getPassword().equalsIgnoreCase(md5password))){
            request.setAttribute("msg","用户名或密码错误");
            return Constants.FORWARD+"/login.jsp";
        }
        if(user.getStatus().equals(Constants.USER_NOT_ACTIVE)){
            request.setAttribute("msg","账号未激活");
            return Constants.FORWARD+"/login.jsp";
        }
        session.setAttribute("loginUser",user);
        if(auto==null){
            Cookie cookie = new Cookie(Constants.AUTO_NAME,"");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }else{
            String content = username+Constants.FLAG+password;
            content = Base64Utils.encode(content);
            Cookie cookie = new Cookie(Constants.AUTO_NAME,content);
            cookie.setPath("/");
            cookie.setMaxAge(14*24*60*60);
            response.addCookie(cookie);
        }
        return Constants.REDIRECT+"/index.jsp";
    }
    public String deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String uid = request.getParameter("uid");
        boolean flag = userService.deleteUser(Integer.parseInt(uid));
        return "" + flag;
    }
}
