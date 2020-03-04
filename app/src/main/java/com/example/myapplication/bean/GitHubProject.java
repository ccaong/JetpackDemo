package com.example.myapplication.bean;

/**
 * @author : devel
 * @date : 2020/3/3 14:57
 * @desc :开源项目
 */
public class GitHubProject {

    private String name;

    private String info;

    private String address;

    public GitHubProject() {
    }

    public GitHubProject(String name, String info, String address) {
        this.name = name;
        this.info = info;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
