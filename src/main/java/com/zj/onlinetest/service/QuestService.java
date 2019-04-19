package com.zj.onlinetest.service;

import com.zj.onlinetest.domain.Question;

import java.util.List;

/**
 * @Auther: zj
 * @Date: 2019/4/17 14:00
 * @Description:
 */
public interface QuestService {

    Question add(String id, String subject, Long createTime);

    Question selectOneById(String id);

    List<Question> selectAll();
}
