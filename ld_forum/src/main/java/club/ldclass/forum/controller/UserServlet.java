package club.ldclass.forum.controller;

import club.ldclass.forum.domain.User;
import club.ldclass.forum.service.UserService;
import club.ldclass.forum.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName UserServlet
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 18:43
 * @Version 1.0
 **/
@WebServlet(name = "userServlet",urlPatterns = {"/user"})
public class UserServlet extends BaseServlet{

    private UserService userService = new UserServiceImpl();

    /**
     * 注册
     * @param request
     * @param response
     */
    public void  register(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        Map<String, String[]> map = request.getParameterMap();
        try{
            BeanUtils.populate(user,map);
        }catch(Exception e){
            e.printStackTrace();
        }
        int i = userService.register(user);
        if(i>0){
            //注册成功，跳转到登录界面
            request.getRequestDispatcher("/user/login.jsp").forward(request,response);
        }else{
            //注册失败，跳转到注册界面
            request.getRequestDispatcher("/user/register.jsp").forward(request,response);
        }
    }

    /**
     * 登录
     */
    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        String phone = request.getParameter("phone");
        String pwd = request.getParameter("pwd");
        User user = userService.login(phone,pwd);
        if(user != null){
            request.getSession().setAttribute("loginUser",user);
            //跳转页面
            response.sendRedirect("/topic?method=list&c_id=1");
        }else{
            request.setAttribute("msg","用户名或密码不正确");
            request.getRequestDispatcher("/user/login.jsp").forward(request,response);
        }
    }

    /**
     * 注销
     * @param request
     * @param response
     */
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        //页面跳转
        response.sendRedirect("/topic?method=list&c_id=1");
    }
}
