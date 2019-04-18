package com.zj.onlinetest.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Auther: zj
 * @Date: 2019/4/17 13:55
 * @Description:
 */
@Entity
public class Question {

    @Id
    private String id;

    private String subject;

    private String answer;

    private Long createTime;

    public Question() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
