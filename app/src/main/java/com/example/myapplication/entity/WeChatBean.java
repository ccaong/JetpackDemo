package com.example.myapplication.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "wechat")
public class WeChatBean {
    /**
     * children : []
     * courseId : 13
     * id : 408
     * name : 鸿洋
     * order : 190000
     * parentChapterId : 407
     * userControlSetTop : false
     * visible : 1
     */


    @PrimaryKey
    private int id;
    private int courseId;
    private String name;
    private int order;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;
//    private List<?> children;

    public WeChatBean() {

    }

    @Ignore
    public WeChatBean(int id, int courseId, String name, int order, int parentChapterId, boolean userControlSetTop, int visible) {
        this.id = id;
        this.courseId = courseId;
        this.name = name;
        this.order = order;
        this.parentChapterId = parentChapterId;
        this.userControlSetTop = userControlSetTop;
        this.visible = visible;
//        this.children = children;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

//    public List<?> getChildren() {
//        return children;
//    }

//    public void setChildren(List<?> children) {
//        this.children = children;
//    }
}
