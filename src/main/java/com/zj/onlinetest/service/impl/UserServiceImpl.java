package com.zj.onlinetest.service.impl;

import com.zj.onlinetest.Repository.UserRepository;
import com.zj.onlinetest.domain.User;
import com.zj.onlinetest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * @Auther: zj
 * @Date: 2019/4/17 11:06
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public User saveOrUpdate(User user) {
        return userRepository.save( user );
    }

    @Override
    public User selectOneByUsername(String username) {
        return userRepository.findOneByUsername( username );
    }

    @Override
    public User addNewUser(String id, String username, Long createTime) {
        User user = new User();
        user.setId( id );
        user.setUsername( username );
        user.setRole( "ROLE_USER" );
        user.setPassword( DigestUtils.md5DigestAsHex("111".getBytes() ));
        user.setLimitTimes( 1 );
        user.setCreateTime( createTime );

        userRepository.save( user );
        return user;
    }

    @Override
    public User selectOneById(String id) {
        User user = userRepository.findOneById(id);
        return user;
    }

    @Override
    public User changeUser(User user,Integer number, String questions,String testUrl) {
        user.setNumber( number );
        user.setQuestions( questions );
        user.setTestUrl(testUrl);
        userRepository.save( user );
        return user;
    }

    @Override
    public List<User> selectAllUser(Integer pageIndex) {
        Pageable pageable = PageRequest.of( pageIndex,5);
        return userRepository.findAll( pageable ).getContent();
    }

    @Override
    public List<User> selectAllUser() {
        return userRepository.findAll();
    }
}
