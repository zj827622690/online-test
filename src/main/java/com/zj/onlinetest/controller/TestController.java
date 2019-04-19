package com.zj.onlinetest.controller;

import com.zj.onlinetest.Repository.UserRepository;
import com.zj.onlinetest.domain.User;
import com.zj.onlinetest.rabbitmq.Sender;
import com.zj.onlinetest.utils.JsonUtils;
import com.zj.onlinetest.utils.RandomUtils;
import com.zj.onlinetest.utils.TimeUtils;
import com.zj.onlinetest.vo.MqTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * @Auther: zj
 * @Date: 2019/4/16 22:45
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Sender sender;

    @GetMapping("/aaa")
    public String test() {
        //HttpServletResponse.SC_METHOD_NOT_ALLOWED 状态码

//        String aaa =RandomUtils.number( 6 );

//        Long millisecond = Instant.now().toEpochMilli();  // 获取当前时间，精确到毫秒
//
//        System.out.println( millisecond );
//
//        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        System.out.println(ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.systemDefault())));

        Long aaa=TimeUtils.getNow();
        System.out.println(aaa);

        System.out.println( new Date(aaa) );

//        System.out.println( Timeutils.convertTimeToLong(Timeutils.timeToString( aaa )   ));

        return "111";
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

    @GetMapping("/test/666")
    public String adqwjj() {
        MqTask mqTask = new MqTask();
        mqTask.setData( "这是来自消息队列的消息1111" );
        mqTask.setUid("666");

        sender.send( JsonUtils.objectToJson( mqTask  ) );
        return "111";
    }

}
