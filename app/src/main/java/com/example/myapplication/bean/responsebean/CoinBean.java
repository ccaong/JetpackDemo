package com.example.myapplication.bean.responsebean;

import java.util.List;

/**
 * @author : devel
 * @date : 2020/2/26 15:13
 * @desc : 积分获取情况详情
 */
public class CoinBean {


    /**
     * curPage : 0
     * datas : [{"coinCount":19,"date":1582677961000,"desc":"2020-02-26 08:46:01 签到 , 积分：10 + 9","id":150034,"reason":"签到","type":1,"userId":44330,"userName":"ccaong@outlook.com"},{"coinCount":18,"date":1582591395000,"desc":"2020-02-25 08:43:15 签到 , 积分：10 + 8","id":149508,"reason":"签到","type":1,"userId":44330,"userName":"ccaong@outlook.com"},{"coinCount":17,"date":1582474656000,"desc":"2020-02-24 00:17:36 签到 , 积分：10 + 7","id":148530,"reason":"签到","type":1,"userId":44330,"userName":"ccaong@outlook.com"},{"coinCount":16,"date":1582387793000,"desc":"2020-02-23 00:09:53 签到 , 积分：10 + 6","id":148036,"reason":"签到","type":1,"userId":44330,"userName":"ccaong@outlook.com"},{"coinCount":15,"date":1582358964000,"desc":"2020-02-22 16:09:24 签到 , 积分：10 + 5","id":147887,"reason":"签到","type":1,"userId":44330,"userName":"ccaong@outlook.com"},{"coinCount":14,"date":1582249854000,"desc":"2020-02-21 09:50:54 签到 , 积分：10 + 4","id":147273,"reason":"签到","type":1,"userId":44330,"userName":"ccaong@outlook.com"},{"coinCount":13,"date":1582163220000,"desc":"2020-02-20 09:47:00 签到 , 积分：10 + 3","id":146776,"reason":"签到","type":1,"userId":44330,"userName":"ccaong@outlook.com"},{"coinCount":12,"date":1582110015000,"desc":"2020-02-19 19:00:15 签到 , 积分：10 + 2","id":146564,"reason":"签到","type":1,"userId":44330,"userName":"ccaong@outlook.com"},{"coinCount":11,"date":1581819457000,"desc":"2020-02-16 10:17:37 签到 , 积分：10 + 1","id":144777,"reason":"签到","type":1,"userId":44330,"userName":"ccaong@outlook.com"},{"coinCount":10,"date":1581743798000,"desc":"2020-02-15 13:16:38 签到 , 积分：10 + 0","id":144516,"reason":"签到","type":1,"userId":44330,"userName":"ccaong@outlook.com"}]
     * offset : -20
     * over : false
     * pageCount : 1
     * size : 20
     * total : 10
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<CoinDataBean> datas;

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

    public List<CoinDataBean> getDatas() {
        return datas;
    }

    public void setDatas(List<CoinDataBean> datas) {
        this.datas = datas;
    }

    public static class CoinDataBean {
        /**
         * coinCount : 19
         * date : 1582677961000
         * desc : 2020-02-26 08:46:01 签到 , 积分：10 + 9
         * id : 150034
         * reason : 签到
         * type : 1
         * userId : 44330
         * userName : ccaong@outlook.com
         */

        private int coinCount;
        private long date;
        private String desc;
        private int id;
        private String reason;
        private int type;
        private int userId;
        private String userName;

        public int getCoinCount() {
            return coinCount;
        }

        public void setCoinCount(int coinCount) {
            this.coinCount = coinCount;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
