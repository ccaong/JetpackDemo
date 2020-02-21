package com.example.myapplication.entity;

import java.util.List;

/**
 * @author : devel
 * @date : 2020/2/21 14:55
 * @desc :
 */
public class City {


    private List<CityBean> city;

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }


    public static class CityBean {
        /**
         * value : 1
         * parent : null
         * label : 安徽省
         */

        private int value;
        private Object parent;
        private String label;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Object getParent() {
            return parent;
        }

        public void setParent(Object parent) {
            this.parent = parent;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
