package com.example.myapplication.util;

import java.util.List;

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
}
