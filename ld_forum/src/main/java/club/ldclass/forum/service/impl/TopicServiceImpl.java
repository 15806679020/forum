package club.ldclass.forum.service.impl;

import club.ldclass.forum.dao.CategoryDao;
import club.ldclass.forum.dao.ReplyDao;
import club.ldclass.forum.dao.TopicDao;
import club.ldclass.forum.domain.Category;
import club.ldclass.forum.domain.Reply;
import club.ldclass.forum.domain.Topic;
import club.ldclass.forum.domain.User;
import club.ldclass.forum.dto.PageDTO;
import club.ldclass.forum.service.TopicService;

import java.util.Date;
import java.util.List;

/**
 * @ClassName TopicServiceImpl
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 17:45
 * @Version 1.0
 **/
public class TopicServiceImpl implements TopicService {
    private TopicDao topicDao = new TopicDao();
    private ReplyDao replyDao = new ReplyDao();
    private CategoryDao categoryDao = new CategoryDao();

    @Override
    public PageDTO<Topic> listTopicPageByCid(int cId, int page, int pageSize) {
        //查询总记录数
        int totalRecordNum = topicDao.countTotalTopicByCid(cId);
        //从哪条开始取数据
        int from = (page - 1) * pageSize;
        //分页查询
        List<Topic> topicList = topicDao.findListByCid(cId, from, pageSize);
        PageDTO<Topic> pageDTO = new PageDTO<>(page, pageSize, totalRecordNum);
        pageDTO.setList(topicList);
        return pageDTO;
    }

    @Override
    public Topic findById(int topicId) {
        return topicDao.findById(topicId);
    }

    @Override
    public PageDTO<Reply> findReplyPageByTopicId(int topicId, int page, int pageSize) {
        //查询总的回复数
        int totalRecordNum = replyDao.countTotalReplyByTopicId(topicId);
        int from = (page - 1) * pageSize;
        List<Reply> replyList = replyDao.findListByTopicId(topicId, from, pageSize);
        PageDTO<Reply> pageDTO = new PageDTO<>(page, pageSize, totalRecordNum);
        pageDTO.setList(replyList);
        return pageDTO;
    }

    @Override
    public int addTopic(User loginUser, String title, String content, int cId) {
        Category category = categoryDao.findById(cId);
        if(category == null){ return 0;}
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setCreateTime(new Date());
        topic.setUpdateTime(new Date());
        topic.setPv(1);
        topic.setDelete(0);
        topic.setUserId(loginUser.getId());
        topic.setUsername(loginUser.getUsername());
        topic.setUserImg(loginUser.getImg());
        topic.setcId(cId);
        topic.setHot(0);
        int rows = 0;
        try {
            rows = topicDao.save(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public int replyByTopicId(User loginUser, int topicId, String content) {
        int floor = topicDao.findLatestFloorByTopicId(topicId);
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setCreateTime(new Date());
        reply.setUpdateTime(new Date());
        reply.setFloor(floor+1);
        reply.setTopicId(topicId);
        reply.setUserId(loginUser.getId());
        reply.setUsername(loginUser.getUsername());
        reply.setUserImg(loginUser.getImg());
        reply.setDelete(0);
        int rows = replyDao.save(reply);
        return rows;
    }

    @Override
    public void addOnePV(int topicId) {
        Topic topic = topicDao.findById(topicId);
        int newPV = topic.getPv()+1;
        topicDao.updatePV(topicId,newPV,topic.getPv());
    }
}
