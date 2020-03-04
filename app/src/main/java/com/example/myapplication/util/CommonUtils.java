package com.example.myapplication.util;

import com.example.myapplication.config.App;

import java.util.List;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

/**
 * 工具类
 */
public final class CommonUtils {

    private CommonUtils() {

    }

    /**
     * 判断列表是否为空
     *
     * @param list 列表
     * @return true表示为空，false表示不为空
     */
    public static boolean isListEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    public static boolean isStringEmpty(String string) {
        return string == null || "".equals(string);
    }

    /**
     * 获取字符串资源
     *
     * @param resId 字符串资源ID
     * @return 字符串
     */
    public static String getString(@StringRes int resId) {
        return App.getContext().getString(resId);
    }

    /**
     * 获取字符串资源
     *
     * @param i
     * @return 字符串
     */
    public static String int2String(@StringRes int i) {
        return i + "";
    }


    /**
     * 获取颜色资源
     *
     * @param resId 颜色资源ID
     * @return 颜色
     */
    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(App.getContext(), resId);
    }

}
