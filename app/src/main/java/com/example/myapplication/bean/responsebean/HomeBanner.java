package com.example.myapplication.bean.responsebean;

/**
 * 主页Banner信息
 */
public class HomeBanner {
    /**
     * desc : 享学~
     * id : 29
     * imagePath : https://www.wanandroid.com/blogimgs/ade5fbf7-57d7-4fec-ad02-402dcf12acd2.jpeg
     * isVisible : 1
     * order : 0
     * title : 今年找工作会更难吗？
     * type : 0
     * url : https://mp.weixin.qq.com/s/cASgy1B6yfIu9v6usm7rPA
     */

    private String desc;
    private int id;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
