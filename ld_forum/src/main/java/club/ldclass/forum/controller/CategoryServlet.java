package club.ldclass.forum.controller;


import club.ldclass.forum.domain.Category;
import club.ldclass.forum.service.CategoryService;
import club.ldclass.forum.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName CategoryServlet
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 16:06
 * @Version 1.0
 **/
@WebServlet(name = "categoryServlet", urlPatterns = {"/category"})
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 返回全部分类
     */
    public void list(HttpServletRequest req, HttpServletResponse resp) {
        //TODO
        List<Category> categories = categoryService.list();
        System.out.println(categories.toString());
        req.setAttribute("categoryList",categories);
    }
}
