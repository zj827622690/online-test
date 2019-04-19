package com.zj.onlinetest.rabbitmq;

import com.zj.onlinetest.domain.Record;
import com.zj.onlinetest.service.RecordService;
import com.zj.onlinetest.utils.JsonUtils;
import com.zj.onlinetest.utils.RandomUtils;
import com.zj.onlinetest.utils.TimeUtils;
import com.zj.onlinetest.vo.RequestMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;


/**
 * @Auther: zj
 * @Date: 2019/4/17 12:53
 * @Description: 消息消费者
 */
@Component
@RabbitListener(queues = "hello")
public class Receiver {


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    RecordService recordService;

    @RabbitHandler
    public void process(String context) throws IOException {
        System.out.println("Receiver : " + context);

        RequestMessage mqTask = new RequestMessage(  );
        BeanUtils.copyProperties( JsonUtils.jsonToObject( context,RequestMessage.class ),mqTask );

        if (Objects.equals( mqTask.getType(), "1" )) {
            Record record = new Record( RandomUtils.number( 9 ),
                                        mqTask.getUserId(),
                                        mqTask.getQuestionId(),
                                        mqTask.getContent(),
                                        null,null,
                                        TimeUtils.getNow());
            recordService.saveOrUpdate( record );
        }

        if (Objects.equals( mqTask.getType(), "2" )) {
            String destination = "/topic/" +mqTask.getRoom();

            messagingTemplate.convertAndSend( destination, mqTask);
        }





    }

}
