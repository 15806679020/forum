package club.ldclass.forum.service.impl;

import club.ldclass.forum.dao.CategoryDao;
import club.ldclass.forum.domain.Category;
import club.ldclass.forum.service.CategoryService;

import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 16:17
 * @Version 1.0
 **/
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDao();

    /**
     * 查询全部分类
     */
    @Override
    public List<Category> list() {
        return categoryDao.list();
    }
}
