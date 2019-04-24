package com.zj.onlinetest.Repository;

import com.zj.onlinetest.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Auther: zj
 * @Date: 2019/4/17 13:59
 * @Description:
 */
public interface QuestionRepository extends JpaRepository<Question,String>{

    Question findOneById(String  id);

    Page<Question> findAllByOrderByCreateTimeDesc(Pageable pageable);

    List<Question> findAllByOrderByCreateTimeDesc();

}
