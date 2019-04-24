package com.zj.onlinetest.controller;

import com.zj.onlinetest.Repository.UserRepository;
import com.zj.onlinetest.domain.User;
import com.zj.onlinetest.rabbitmq.Sender;
import com.zj.onlinetest.utils.RandomUtils;
import com.zj.onlinetest.utils.TimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Auther: zj
 * @Date: 2019/4/16 22:45
 * @Description: 仅用来测试
 */
@RestController
//@RequestMapping("/test")
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Sender sender;

    @GetMapping("/test/aaa")
    public String testad() {
        return "111";
    }

    @GetMapping("/aaa")
    public String testadaa() {
        return "111";
    }

    @GetMapping("/test/html")
    public void testhtml(HttpServletResponse response) throws IOException {
        response.sendRedirect( "/static/webSocketTest.html" );
    }


    @GetMapping("/create")
    public String create() {
        User user = new User();
        user.setId( RandomUtils.number( 9 ) );
        user.setUsername( "aaa" );

        user.setCreateTime( TimeUtils.getNow() );

        userRepository.save( user );

        return "插入用户记录成功！";
    }

//    @GetMapping("/test/666")
//    public String adqwjj() {
//        MqTask mqTask = new MqTask();
//        mqTask.setData( "这是来自消息队列的消息1111" );
//        mqTask.setUid("666");
//
//        sender.send( JsonUtils.objectToJson( mqTask  ) );
//        return "111";
//    }





}
