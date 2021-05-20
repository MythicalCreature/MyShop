package myshop.controller;

import cn.dsna.util.images.ValidateCode;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author zzx
 * 2020/12/6 10:28
 */
@WebServlet("/code")
public class CodeController extends BaseServlet {
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ValidateCode validateCode = new ValidateCode(100,35,4,20);
        String code = validateCode.getCode();
        request.getSession().setAttribute("code",code);
        validateCode.write(response.getOutputStream());
    }
}
