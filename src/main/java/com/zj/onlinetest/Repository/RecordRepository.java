package com.zj.onlinetest.Repository;

import com.zj.onlinetest.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: zj
 * @Date: 2019/4/17 14:20
 * @Description:
 */
public interface RecordRepository extends JpaRepository<Record,String>{
}
