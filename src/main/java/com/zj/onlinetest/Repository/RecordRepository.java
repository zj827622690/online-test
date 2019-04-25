package com.zj.onlinetest.Repository;

import com.zj.onlinetest.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Auther: zj
 * @Date: 2019/4/17 14:20
 * @Description:
 */
public interface RecordRepository extends JpaRepository<Record,String>{

    List<Record> findAllByUserIdAndQuestionIdOrderByCreateTimeAsc(String userId,String questionId);

    List<Record> findAllByUserIdAndQuestionIdOrderByCreateTimeDesc(String userId,String questionId);

}
