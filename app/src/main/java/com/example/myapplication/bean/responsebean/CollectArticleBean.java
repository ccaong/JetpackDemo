package com.example.myapplication.bean.responsebean;

import java.util.List;

/**
 * 收藏的文章
 */
public class CollectArticleBean {

    /**
     * curPage : 1
     * datas : [{"author":"","chapterId":26,"chapterName":"基础UI控件","courseId":13,"desc":"","envelopePic":"","id":118545,"link":"https://juejin.im/post/5dd6176c6fb9a05a9d6bf2ba","niceDate":"刚刚","origin":"","originId":10422,"publishTime":1583047398000,"title":"ViewStub你真的了解吗","userId":44330,"visible":0,"zan":0},{"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","courseId":13,"desc":"","envelopePic":"","id":118544,"link":"https://mp.weixin.qq.com/s/0lgYPvwL1B6ohvKoBHC0oQ","niceDate":"刚刚","origin":"","originId":12096,"publishTime":1583047393000,"title":"&ldquo;吹上天&rdquo;的Kotlin协程 要不看下实战？","userId":44330,"visible":0,"zan":0},{"author":"gs666","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"一个模仿企鹅 FM 界面的Android 应用&mdash;喜马拉雅Kotlin。完全使用 Kotlin 开发。有声资源和播放器由喜马拉雅 SDK 提供。 主要功能：\r\n\r\n1,在线播放专辑点播\r\n2,在线播放国家/省/市广播电台\r\n3,最近收听\r\n4,搜索节目/专辑/广播（包括热搜词）","envelopePic":"https://wanandroid.com/blogimgs/2baa4b4b-acfe-473c-b534-9d672423e564.png","id":118543,"link":"http://www.wanandroid.com/blog/show/2703","niceDate":"1分钟前","origin":"","originId":10135,"publishTime":1583047305000,"title":"一个模仿企鹅 FM 界面的 Android 应用&mdash;喜马拉雅Kotlin。完全使用 Kotlin 开发。","userId":44330,"visible":0,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 3
     */
    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<CollectBean> datas;

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

    public List<CollectBean> getDatas() {
        return datas;
    }

    public void setDatas(List<CollectBean> datas) {
        this.datas = datas;
    }

    public static class CollectBean {
        /**
         * author :
         * chapterId : 26
         * chapterName : 基础UI控件
         * courseId : 13
         * desc :
         * envelopePic :
         * id : 118545
         * link : https://juejin.im/post/5dd6176c6fb9a05a9d6bf2ba
         * niceDate : 刚刚
         * origin :
         * originId : 10422
         * publishTime : 1583047398000
         * title : ViewStub你真的了解吗
         * userId : 44330
         * visible : 0
         * zan : 0
         */

        private String author;
        private int chapterId;
        private String chapterName;
        private int courseId;
        private String desc;
        private String envelopePic;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private int originId;
        private long publishTime;
        private String title;
        private int userId;
        private int visible;
        private int zan;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public int getOriginId() {
            return originId;
        }

        public void setOriginId(int originId) {
            this.originId = originId;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }
    }
}
