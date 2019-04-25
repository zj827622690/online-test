package com.zj.onlinetest.service;

import com.sun.org.apache.regexp.internal.RE;
import com.zj.onlinetest.domain.Record;

/**
 * @Auther: zj
 * @Date: 2019/4/17 14:23
 * @Description:
 */
public interface RecordService {

    Void saveOrUpdate(Record record);

    Record selectTheLastOne(String userId, String questionId);

}
