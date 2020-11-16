package club.ldclass.forum.dao;

import club.ldclass.forum.domain.Reply;
import club.ldclass.forum.domain.Topic;
import club.ldclass.forum.util.DataSourceUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName ReplyDao
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 17:48
 * @Version 1.0
 **/
public class ReplyDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

    //开启驼峰映射
    private BeanProcessor bean = new GenerousBeanProcessor();
    private RowProcessor processor = new BasicRowProcessor(bean);
    /**
     * 根据topic查询回复总行数
     *
     * @param topicId
     * @return
     */
    public int countTotalReplyByTopicId(int topicId) {
        String sql = "select count(*) from reply where topic_id = ?";
        Long count = 0L;
        try {
            count = (Long) queryRunner.query(sql, new ScalarHandler<>(), topicId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count.intValue();
    }

    /**
     * 根据topicId分页查询回复信息
     * @param topicId
     * @param from
     * @param pageSize
     * @return
     */
    public List<Reply> findListByTopicId(int topicId, int from, int pageSize) {
        String sql = "select * from reply where topic_id=? order by create_time asc limit ?,?";
        List<Reply> replyList = null;
        try{
            replyList = queryRunner.query(sql,new BeanListHandler<>(Reply.class,processor),topicId,from,pageSize);
        }catch(Exception e){
            e.printStackTrace();
        }
        return replyList;
    }

    public int save(Reply reply) {
        String sql = "insert into reply (topic_id,floor,content,user_id,username,user_img,create_time,update_time,`delete`)" +
                "values (?,?,?,?,?,?,?,?,?)";
        Object [] params = {
                reply.getTopicId(),
                reply.getFloor(),
                reply.getContent(),
                reply.getUserId(),
                reply.getUsername(),
                reply.getUserImg(),
                reply.getCreateTime(),
                reply.getUpdateTime(),
                reply.getDelete()
        };
        int rows = 0;
        try{
            rows = queryRunner.update(sql,params);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rows;
    }
}
