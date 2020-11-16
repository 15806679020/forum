package club.ldclass.forum.service.impl;

import club.ldclass.forum.dao.UserDao;
import club.ldclass.forum.domain.User;
import club.ldclass.forum.service.UserService;
import club.ldclass.forum.util.CommonUtil;

import java.util.Date;
import java.util.Random;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author LD
 * @Date 2020/11/15 18:47
 * @Version 1.0
 **/
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDao();

    @Override
    public int register(User user) {
        user.setRole(1);
        user.setCreateTime(new Date());
        user.setImg(getRandomImg());
        user.setPwd(CommonUtil.MD5(user.getPwd()));
        try {
            return userDao.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public User login(String phone, String pwd) {
        String md5Pwd = CommonUtil.MD5(pwd);
        User user = userDao.findByPhoneAndPwd(phone, md5Pwd);
        return user;
    }

    /**
     * 放在CDN上的随机头像
     */
    private static final String[] headImg = {
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/12.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/11.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/13.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/14.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/15.jpeg"
    };

    /**
     * 获取随机头像
     *
     * @return
     */
    private String getRandomImg() {
        int size = headImg.length;
        Random random = new Random();
        int index = random.nextInt(size);
        return headImg[index];
    }
}
