package club.ldclass.forum.controller;

import club.ldclass.forum.domain.Reply;
import club.ldclass.forum.domain.Topic;
import club.ldclass.forum.domain.User;
import club.ldclass.forum.dto.PageDTO;
import club.ldclass.forum.service.CategoryService;
import club.ldclass.forum.service.TopicService;
import club.ldclass.forum.service.impl.CategoryServiceImpl;
import club.ldclass.forum.service.impl.TopicServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName TopicServlet
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 17:36
 * @Version 1.0
 **/
@WebServlet(name = "topicServlet", urlPatterns = {"/topic"})
public class TopicServlet extends BaseServlet {
    private TopicService topicService = new TopicServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();
    /**
     * 默认分页大小
     */
    private static final int PAGE_SIZE = 10;

    /**
     * 分页查询主题
     *
     * @param req
     * @param resp
     */
    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cId = Integer.parseInt(req.getParameter("c_id"));
        //默认第一页
        int page = 1;
        String currentPage = req.getParameter("page");
        if (currentPage != null && !"".equals(currentPage)) {
            page = Integer.parseInt(currentPage);
        }
        PageDTO<Topic> pageDTO = topicService.listTopicPageByCid(cId, page, PAGE_SIZE);
        System.out.println(pageDTO.toString());
        req.getSession().setAttribute("categoryList", categoryService.list());
        req.setAttribute("topicPage", pageDTO);
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }

    /**
     * 查看主题详情
     *
     * @param req
     * @param resp
     */
    public void findDetailById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int topicId = Integer.parseInt(req.getParameter("topic_id"));
        //默认第一页
        int page = 1;
        String currentPage = req.getParameter("page");
        if (currentPage != null && !"".equals(currentPage)) {
            page = Integer.parseInt(currentPage);
        }
        //处理浏览量，如果同个session只算一个
        String sessionReadKey = "is_read_" + topicId;
        Boolean isRead = (Boolean) req.getSession().getAttribute(sessionReadKey);
        if (isRead == null) {
            req.getSession().setAttribute(sessionReadKey, true);
            //新增一个pv
            topicService.addOnePV(topicId);
        }

        Topic topic = topicService.findById(topicId);
        PageDTO<Reply> pageDTO = topicService.findReplyPageByTopicId(topicId, page, PAGE_SIZE);
        System.out.println(pageDTO.toString());
        req.setAttribute("topic", topic);
        req.setAttribute("replyPage", pageDTO);
        req.getRequestDispatcher("/topic_detail.jsp").forward(req,resp);
    }

    /**
     * 发布主题
     *
     * @param request
     * @param response
     */
    public void addTopic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            request.setAttribute("msg", "请登录");
            //页面跳转
            response.sendRedirect("/user/login.jsp");
            return;
        }
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int cId = Integer.parseInt(request.getParameter("c_id"));
        int rows = topicService.addTopic(loginUser, title, content, cId);
        //发布主题成功
        response.sendRedirect("/topic?method=list&c_id="+cId);
    }

    /**
     * 盖楼回复
     *
     * @param request
     * @param response
     */
    public void replyByTopicId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            request.setAttribute("msg", "请登录");
            //页面跳转
            response.sendRedirect("/user/login.jsp");
            return;
        }
        int topicId = Integer.parseInt(request.getParameter("topic_id"));
        String content = request.getParameter("content");
        int rows = topicService.replyByTopicId(loginUser, topicId, content);
        //回复成功
        response.sendRedirect("/topic?method=findDetailById&topic_id="+topicId+"&page=1");
    }
}
