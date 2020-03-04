package com.example.myapplication.bean;

/**
 * @author : devel
 * @date : 2020/3/3 15:29
 * @desc :
 */
public class UpdatePlan {

    private String plan;

    private Boolean complete;

    public UpdatePlan(String plan, Boolean complete) {
        this.plan = plan;
        this.complete = complete;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
