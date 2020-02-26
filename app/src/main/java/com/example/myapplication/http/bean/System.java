package com.example.myapplication.http.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 体系
 * @author devel
 */
public class System implements Serializable {

    private Integer courseId;
    private Integer id;
    private String name;
    private Integer order;
    private Integer parentChapterId;
    private Boolean userControlSetTop;
    private Integer visible;
    private List<System> children;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(Integer parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public Boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(Boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public List<System> getChildren() {
        return children;
    }

    public void setChildren(List<System> children) {
        this.children = children;
    }
}
