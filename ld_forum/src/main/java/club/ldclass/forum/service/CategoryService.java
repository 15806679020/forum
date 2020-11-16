package club.ldclass.forum.service;

import club.ldclass.forum.domain.Category;

import java.util.List;

/**
 * @ClassName CategoryService
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 16:16
 * @Version 1.0
 **/
public interface CategoryService {
    /**
     * 查询全部分类
     * @return
     */
    List<Category> list();
}
