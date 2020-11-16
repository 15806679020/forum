package club.ldclass.forum.service;

import club.ldclass.forum.domain.Reply;
import club.ldclass.forum.domain.Topic;
import club.ldclass.forum.domain.User;
import club.ldclass.forum.dto.PageDTO;

/**
 * @ClassName TopicService
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 17:44
 * @Version 1.0
 **/
public interface TopicService {
    PageDTO<Topic> listTopicPageByCid(int cId, int page, int pageSize);

    Topic findById(int topicId);

    PageDTO<Reply> findReplyPageByTopicId(int topicId, int page, int pageSize);

    int addTopic(User loginUser, String title, String content, int cId);

    int replyByTopicId(User loginUser, int topicId, String content);

    void addOnePV(int topicId);
}
