package com.zj.onlinetest.vo;

/**
 * @Auther: zj
 * @Date: 2018/12/22 13:50
 * @Description: 自定义mq消息类
 */
public class MqTask {

    private String data;

    private String uid;

    private String type;

    public MqTask() {

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
