package com.example.myapplication.requestbean;

import java.io.Serializable;
import java.util.Map;

public class LoginData implements Serializable {


    /**
     * loginName : admin
     * userName : 系统管理员
     * userId : admin
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE1NDA3OTkxNTA4OTgsImlhdCI6MTU0MDc5NzM1MDg5OCwiam9iTnVtYmVyIjoiYWRtaW4ifQ.W4xumKb-TLf9dWQgTh-ycrXhbS28sF-ciVG0cbkrl7o
     */

    private String loginName;
    private String userName;
    private String userId;
    private String token;
    private String password;
    private Map funtionMap;

    public Map getFuntionMap() {
        return funtionMap;
    }

    public void setFuntionMap(Map funtionMap) {
        this.funtionMap = funtionMap;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
