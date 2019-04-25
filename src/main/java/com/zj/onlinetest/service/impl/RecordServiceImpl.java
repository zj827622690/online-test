package com.zj.onlinetest.service.impl;

import com.zj.onlinetest.Repository.RecordRepository;
import com.zj.onlinetest.domain.Record;
import com.zj.onlinetest.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: zj
 * @Date: 2019/4/17 14:23
 * @Description:
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordRepository recordRepository;
    @Override
    public Void saveOrUpdate(Record record) {
        recordRepository.save( record );
        return null;
    }

    @Override
    public Record selectTheLastOne(String userId, String questionId) {
        List<Record> list = recordRepository.findAllByUserIdAndQuestionIdOrderByCreateTimeDesc( userId,questionId );
        return list.get( 0 );
    }
}
