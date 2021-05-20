package myshop.filter;

import myshop.entity.User;
import myshop.service.UserService;
import myshop.service.impl.UserServiceImpl;
import myshop.utils.Base64Utils;
import myshop.utils.Constants;
import myshop.utils.MD5Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author zzx
 * 2020/12/6 14:25
 */
@WebFilter("/login.jsp")
public class AutoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie[] cookies = request.getCookies();
        if(cookies==null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            String content = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.AUTO_NAME)) {
                    content = cookie.getValue();
                }
            }
            if(content!=null){
                content  = Base64Utils.decode(content);
                String[] split = content.split(Constants.FLAG);
                String username = split[0];
                String password = split[1];
                UserService userService = new UserServiceImpl();
                try {
                    User user = userService.loginUser(username);
                    String md5password = MD5Utils.md5(password);
                    if(user!=null&&user.getPassword().equalsIgnoreCase(md5password)){
                        HttpSession session = request.getSession();
                        session.setAttribute("loginUser",user);
                        HttpServletResponse response = (HttpServletResponse)servletResponse;
                        response.sendRedirect(request.getContextPath()+"/index.jsp");
                    }else{
                        filterChain.doFilter(servletRequest,servletResponse);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
