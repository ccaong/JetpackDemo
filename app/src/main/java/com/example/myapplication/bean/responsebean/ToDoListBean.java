package com.example.myapplication.bean.responsebean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : devel
 * @date : 2020/2/24 11:49
 * @desc : 待办列表
 */
public class ToDoListBean {


    /**
     * curPage : 0
     * datas : [{"completeDate":null,"completeDateStr":"","content":"1,列表查询\n2,新增","date":1582473600000,"dateStr":"2020-02-24","id":21922,"priority":0,"status":0,"title":"todoFragment","type":2,"userId":44330},{"completeDate":null,"completeDateStr":"","content":"更新侧边栏\n修改主页","date":1582473600000,"dateStr":"2020-02-24","id":21923,"priority":0,"status":0,"title":"主页","type":1,"userId":44330}]
     * offset : -20
     * over : false
     * pageCount : 1
     * size : 20
     * total : 2
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<ToDoData> datas;

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

    public List<ToDoData> getDatas() {
        return datas;
    }

    public void setDatas(List<ToDoData> datas) {
        this.datas = datas;
    }

    public static class ToDoData implements Serializable {
        /**
         * completeDate : null
         * completeDateStr :
         * content : 1,列表查询
         2,新增
         * date : 1582473600000
         * dateStr : 2020-02-24
         * id : 21922
         * priority : 0
         * status : 0
         * title : todoFragment
         * type : 2
         * userId : 44330
         */

        private Object completeDate;
        private String completeDateStr;
        private String content;
        private long date;
        private String dateStr;
        private int id;
        private int priority;
        private int status;
        private String title;
        private int type;
        private int userId;

        public Object getCompleteDate() {
            return completeDate;
        }

        public void setCompleteDate(Object completeDate) {
            this.completeDate = completeDate;
        }

        public String getCompleteDateStr() {
            return completeDateStr;
        }

        public void setCompleteDateStr(String completeDateStr) {
            this.completeDateStr = completeDateStr;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getDateStr() {
            return dateStr;
        }

        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
    }
}
