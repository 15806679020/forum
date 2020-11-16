package club.ldclass.forum.dao;

import club.ldclass.forum.domain.User;
import club.ldclass.forum.util.DataSourceUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * @ClassName UserDao
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 18:53
 * @Version 1.0
 **/
public class UserDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
    /**
     * 开启驼峰映射
     */
    private BeanProcessor bean = new GenerousBeanProcessor();
    private RowProcessor processor = new BasicRowProcessor(bean);

    public User findByPhoneAndPwd(String phone, String pwd) {
        String sql = "select * from user where phone=? and pwd = ?";
        User user = null;
        try {
            user = queryRunner.query(sql, new BeanHandler<>(User.class, processor), phone, pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public int saveUser(User user) throws Exception {
        String sql = "insert into user (phone,pwd,sex,img,create_time,role,username) values(?,?,?,?,?,?,?)";
        Object[] params = {
                user.getPhone(),
                user.getPwd(),
                user.getSex(),
                user.getImg(),
                user.getCreateTime(),
                user.getRole(),
                user.getUsername()
        };
        int i = 0;
        try {
            i = queryRunner.update(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
        return i;
    }
}
