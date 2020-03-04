package com.example.myapplication.bean.responsebean;

/**
 * 用户积分信息
 */
public class Coin {

    /**
     * coinCount : 451
     * rank : 7
     * userId : 2
     * username : x**oyang
     */

    private int coinCount;
    private int rank;
    private int userId;
    private String username;


    public String getCoinCount() {
        return coinCount+"分";
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
