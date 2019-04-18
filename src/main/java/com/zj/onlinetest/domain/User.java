package com.zj.onlinetest.domain;


import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Auther: zj
 * @Date: 2019/4/16 23:11
 * @Description:
 */
@Entity
public class User {

    @Id
    private String id;

    private String username;

    private String password;

    /**
     * ROLE_ADMIN
     * ROLE_USER
     */
    private String role;

    /**
     * 限制次数 默认1
     */
    private Integer limitTimes;

    /**
     * 题目的数量
     */
    private Integer number;

    /**
     * 用户需做的题目
     */
    private String questions;

    /**
     * 答题开始时间
     */
    private Long startTime;

    /**
     * 答题停止时间
     */
    private Long endTime;

    private Long createTime;

    public User() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getLimitTimes() {
        return limitTimes;
    }

    public void setLimitTimes(Integer limitTimes) {
        this.limitTimes = limitTimes;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }
}
