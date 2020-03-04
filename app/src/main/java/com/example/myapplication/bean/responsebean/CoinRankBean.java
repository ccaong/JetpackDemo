package com.example.myapplication.bean.responsebean;

import java.util.List;

/**
 * @author : devel
 * @date : 2020/2/26 16:38
 * @desc : 积分排行
 */
public class CoinRankBean {


    /**
     * curPage : 0
     * datas : [{"coinCount":9380,"level":94,"rank":-29,"userId":20382,"username":"g**eii"},{"coinCount":8445,"level":85,"rank":-28,"userId":3559,"username":"A**ilEyon"},{"coinCount":7003,"level":71,"rank":-27,"userId":29303,"username":"深**士"},{"coinCount":6723,"level":68,"rank":-26,"userId":27535,"username":"1**08491840"},{"coinCount":6462,"level":65,"rank":-25,"userId":2,"username":"x**oyang"},{"coinCount":5651,"level":57,"rank":-24,"userId":28694,"username":"c**ng0218"},{"coinCount":5253,"level":53,"rank":-23,"userId":1260,"username":"于**家的吴蜀黍"},{"coinCount":5202,"level":53,"rank":-22,"userId":9621,"username":"S**24n"},{"coinCount":5185,"level":52,"rank":-21,"userId":3753,"username":"S**phenCurry"},{"coinCount":5081,"level":51,"rank":-20,"userId":1534,"username":"j**gbin"},{"coinCount":5058,"level":51,"rank":-19,"userId":863,"username":"m**qitian"},{"coinCount":4993,"level":50,"rank":-18,"userId":25793,"username":"F**_2014"},{"coinCount":4993,"level":50,"rank":-17,"userId":28607,"username":"S**Brother"},{"coinCount":4986,"level":50,"rank":-16,"userId":7710,"username":"i**Cola7"},{"coinCount":4984,"level":50,"rank":-15,"userId":7809,"username":"1**23822235"},{"coinCount":4971,"level":50,"rank":-14,"userId":7891,"username":"h**zkp"},{"coinCount":4951,"level":50,"rank":-13,"userId":14829,"username":"l**changwen"},{"coinCount":4930,"level":50,"rank":-12,"userId":27,"username":"y**ochoo"},{"coinCount":4922,"level":50,"rank":-11,"userId":2068,"username":"i**Cola"},{"coinCount":4893,"level":49,"rank":-10,"userId":12351,"username":"w**igeny"},{"coinCount":4891,"level":49,"rank":-9,"userId":12467,"username":"c**yie"},{"coinCount":4846,"level":49,"rank":-8,"userId":833,"username":"w**lwaywang6"},{"coinCount":4816,"level":49,"rank":-7,"userId":12331,"username":"R**kieJay"},{"coinCount":4816,"level":49,"rank":-6,"userId":25419,"username":"蔡**打篮球"},{"coinCount":4816,"level":49,"rank":-5,"userId":26707,"username":"p**xc.com"},{"coinCount":4731,"level":48,"rank":-4,"userId":6095,"username":"W**derfulMtf"},{"coinCount":4731,"level":48,"rank":-3,"userId":29076,"username":"f**ham"},{"coinCount":4731,"level":48,"rank":-2,"userId":29185,"username":"轻**宇"},{"coinCount":4693,"level":47,"rank":-1,"userId":4662,"username":"1**71599512"},{"coinCount":4690,"level":47,"rank":0,"userId":2160,"username":"R**iner"}]
     * offset : -30
     * over : false
     * pageCount : 680
     * size : 30
     * total : 20374
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<RankDataBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RankDataBean> getDatas() {
        return datas;
    }

    public void setDatas(List<RankDataBean> datas) {
        this.datas = datas;
    }

    public static class RankDataBean {
        /**
         * coinCount : 9380
         * level : 94
         * rank : -29
         * userId : 20382
         * username : g**eii
         */

        private int coinCount;
        private int level;
        private int rank;
        private int userId;
        private String username;

        public int getCoinCount() {
            return coinCount;
        }

        public void setCoinCount(int coinCount) {
            this.coinCount = coinCount;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
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
}
