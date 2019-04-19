package com.zj.onlinetest.rabbitmq;

import com.zj.onlinetest.utils.JsonUtils;
import com.zj.onlinetest.vo.MqTask;
import com.zj.onlinetest.vo.ResponseMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;


/**
 * @Auther: zj
 * @Date: 2018/12/22 12:53
 * @Description:
 */
@Component
@RabbitListener(queues = "hello")
public class Receiver {
//    @Autowired
//    WebSocketServer webSocketServer;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitHandler
    public void process(String context) throws IOException {
        System.out.println("Receiver : " + context);
//        webSocketServer.sendMessage( JsonUtils.jsonToObject( context,MqTask.class).getData(),JsonUtils.jsonToObject( context,MqTask.class ).getUid());
//        messagingTemplate.convertAndSend(destination, response);

        ResponseMessage response = new ResponseMessage("wu", "000", JsonUtils.jsonToObject( context,MqTask.class).getData());
        String destination = "/topic/" +JsonUtils.jsonToObject( context,MqTask.class ).getUid();

        messagingTemplate.convertAndSend( destination, response);
    }

}
