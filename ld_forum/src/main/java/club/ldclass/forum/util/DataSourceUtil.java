package club.ldclass.forum.util;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName DataSourceUtil
 * @Description TODO 配置文件工具类
 * @Author LD
 * @Date 2020/11/15 15:49
 * @Version 1.0
 **/
public class DataSourceUtil {
    private static DataSource dataSource;
    static {
        try {
            InputStream inputStream = DataSourceUtil.class.getClassLoader().getResourceAsStream("database.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("初始化DBPC失败");
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }
}

