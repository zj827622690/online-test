package com.zj.onlinetest.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Auther: zj
 * @Date: 2019/4/17 14:04
 * @Description:
 */
@Entity
public class Record {

    @Id
    private String id;

    private String userId;

    private String questionId;

    private String answer;

    private Long startTime;

    private Long endTime;

    private Long createTime;

    public Record() {

    }

    public Record(String id, String userId, String questionId, String answer, Long startTime, Long endTime, Long createTime) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.answer = answer;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
