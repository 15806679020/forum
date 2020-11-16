package club.ldclass.forum.service;

import club.ldclass.forum.domain.User;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 18:47
 * @Version 1.0
 **/
public interface UserService {

    int register(User user);

    User login(String phone, String pwd);
}
