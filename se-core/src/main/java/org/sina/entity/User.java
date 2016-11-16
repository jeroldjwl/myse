package org.sina.entity;

import java.util.Date;

/**
 * Created by Jerold on 2016/11/15.
 */
public class User {
    private long id;
    private String nickName;
    private Location location;
    private String description;
    private String picSrc;
    private String gender;
    private int fans;
    private int follow;
    private int weiboNum;
    private int collect;
    private Date createDate;
    private String certification;
    private int friends;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public int getWeiboNum() {
        return weiboNum;
    }

    public void setWeiboNum(int weiboNum) {
        this.weiboNum = weiboNum;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public int getFriends() {
        return friends;
    }

    public void setFriends(int friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", location=" + location +
                ", description='" + description + '\'' +
                ", picSrc='" + picSrc + '\'' +
                ", gender='" + gender + '\'' +
                ", fans=" + fans +
                ", follow=" + follow +
                ", weiboNum=" + weiboNum +
                ", collect=" + collect +
                ", createDate=" + createDate +
                ", certification='" + certification + '\'' +
                ", friends=" + friends +
                '}';
    }
}
