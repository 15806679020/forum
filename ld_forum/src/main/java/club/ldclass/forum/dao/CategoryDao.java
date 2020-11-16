package club.ldclass.forum.dao;

import club.ldclass.forum.domain.Category;
import club.ldclass.forum.util.DataSourceUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * @ClassName CategoryDao
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 16:18
 * @Version 1.0
 **/
public class CategoryDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

    //开启驼峰映射
    private BeanProcessor bean = new GenerousBeanProcessor();
    private RowProcessor processor = new BasicRowProcessor(bean);

    /**
     * 根据id查找分类
     *
     * @param id
     * @return
     */
    public Category findById(int id) {
        String sql = "select * from category where id = ?";
        Category category = null;
        try {
            category = queryRunner.query(sql, new BeanHandler<>(Category.class, processor), id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    /**
     * 查询全部分类
     * @return
     */
    public List<Category> list() {
        String sql = "select * from category order by weight desc";
        List<Category> categories = null;
        try{
            categories = queryRunner.query(sql,new BeanListHandler<>(Category.class,processor));
        }catch(Exception e){
            e.printStackTrace();
        }
        return categories;
    }
}
