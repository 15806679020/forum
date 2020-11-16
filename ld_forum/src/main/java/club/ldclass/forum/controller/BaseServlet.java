package club.ldclass.forum.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @ClassName BaseServlet
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 16:04
 * @Version 1.0
 **/
@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {
    /**
     * Description: 子类的Servlet被访问，会调用service方法，子类没有重写，那么就会调用父类的service方法
     * @Date: 2020/11/15 16:11
     * @param req
     * @param resp
     * @return: void
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //获取请求方法
        String method = req.getParameter("method");
        if(method != null){
            try{
                //反射
                //获得当前被访问的对象的字节码对象，和字节码对象里指定的方法
                Method targetMethod = this.getClass().getMethod(method,HttpServletRequest.class,HttpServletResponse.class);
                //执行方法
                targetMethod.invoke(this,req,resp);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
