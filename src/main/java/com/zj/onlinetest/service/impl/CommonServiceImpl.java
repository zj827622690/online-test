package com.zj.onlinetest.service.impl;

import com.zj.onlinetest.Repository.RecordRepository;
import com.zj.onlinetest.domain.Record;
import com.zj.onlinetest.rabbitmq.Sender;
import com.zj.onlinetest.service.CommonService;
import com.zj.onlinetest.service.RecordService;
import com.zj.onlinetest.utils.JsonUtils;
import com.zj.onlinetest.utils.TimeUtils;
import com.zj.onlinetest.vo.RequestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: zj
 * @Date: 2019/4/19 11:24
 * @Description:
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    RecordService recordService;

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    Sender sender;


    @Async("taskExecutor")
    @Override
    public void playbackAnswer(String userId, String questionId,String room) {
        List<Record> recordList=recordRepository.findAllByUserIdAndQuestionIdOrderByCreateTimeAsc(userId,questionId);

        RequestMessage mqTask = new RequestMessage(  );

        for (Record record:recordList) {
            mqTask.setRoom( room );
            mqTask.setUserId( record.getUserId() );
            mqTask.setType( "2" );
            mqTask.setQuestionId( record.getQuestionId() );
            mqTask.setCreateTime( TimeUtils.timeToString(  record.getCreateTime()) );
            mqTask.setContent( record.getAnswer() );

            sender.send( JsonUtils.objectToJson( mqTask ) );
            try {
                Thread.sleep( 1000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
