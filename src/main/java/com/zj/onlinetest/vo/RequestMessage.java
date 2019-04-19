package com.zj.onlinetest.vo;

/**
 * @Auther: zj
 * @Date: 2019/4/18 10:57
 * @Description: 自定义消息类(websocket mq)
 */
public class RequestMessage {

    private String room;//频道号
    private String type;//消息类型('1':客户端到服务端   '2'：客户端到服务端)
    private String content;//消息内容（即答案）
    private String userId;//用户id
    private String questionId;//题目id
    private String createTime;//时间

    public RequestMessage() {
    }

    public RequestMessage(String room, String type, String content, String userId, String questionId, String createTime) {
        this.room = room;
        this.type = type;
        this.content = content;
        this.userId = userId;
        this.questionId = questionId;
        this.createTime = createTime;
    }

    public String getRoom() {
        return room;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

