package com.zj.onlinetest.service;

import com.zj.onlinetest.domain.User;

/**
 * @Auther: zj
 * @Date: 2019/4/17 11:05
 * @Description:
 */
public interface UserService {

    User saveOrUpdate(User user);

    User selectOneByUsername(String username);

    User addNewUser(String id, String username, Long createTime);

    User selectOneById(String id);

    User changeUser(User user,Integer number, String questions);

}
