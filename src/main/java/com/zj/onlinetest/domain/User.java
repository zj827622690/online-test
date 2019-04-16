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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}
