package com.example.myapplication.util;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.lang.reflect.Field;

public class ScreenUtil {


//    public static void adapterScreen(Activity activity,int targetDP,Boolean isVertical){
//        Resources.getSystem().displayMetrics;
//
//    }
//    fun adapterScreen(activity:Activity, targetDP:Int, isVertical:Boolean) {
//        //系统的屏幕尺寸
//        val systemDM = Resources.getSystem().displayMetrics
//        //app整体的屏幕尺寸
//        val appDM = activity.application.resources.displayMetrics
//        //activity的屏幕尺寸
//        val activityDM = activity.resources.displayMetrics
//
//        if (isVertical) {
//            // 适配屏幕的高度
//            activityDM.density = activityDM.heightPixels / targetDP.toFloat()
//        } else {
//            // 适配屏幕的宽度
//            activityDM.density = activityDM.widthPixels / targetDP.toFloat()
//        }
//        // 适配相应比例的字体大小
//        activityDM.scaledDensity = activityDM.density * (systemDM.scaledDensity / systemDM.density)
//        // 适配dpi
//        activityDM.densityDpi = (160 * activityDM.density).toInt()
//    }
//
//    fun resetScreen(activity:Activity) {
//        //系统的屏幕尺寸
//        val systemDM = Resources.getSystem().displayMetrics
//        //app整体的屏幕尺寸
//        val appDM = activity.application.resources.displayMetrics
//        //activity的屏幕尺寸
//        val activityDM = activity.resources.displayMetrics
//
//        activityDM.density = systemDM.density
//        activityDM.scaledDensity = systemDM.scaledDensity
//        activityDM.densityDpi = systemDM.densityDpi
//
//        appDM.density = systemDM.density
//        appDM.scaledDensity = systemDM.scaledDensity
//        appDM.densityDpi = systemDM.densityDpi
//    }
//
//
//    private static List<Field> sMetricsFields;
//
//    private AdaptScreenUtils() {
//        throw new UnsupportedOperationException("u can't instantiate me...");
//    }

//    /**
//     * Adapt for the horizontal screen, and call it in {@link android.app.Activity#getResources()}.
//     */
//    public static Resources adaptWidth(final Resources resources, final int designWidth) {
//        float newXdpi = (resources.getDisplayMetrics().widthPixels * 72f) / designWidth;
//        applyDisplayMetrics(resources, newXdpi);
//        return resources;
//    }

    /**
     * Adapt for the vertical screen, and call it in {@link Activity#getResources()}.
     */
//    public static Resources adaptHeight(final Resources resources, final int designHeight) {
//        return adaptHeight(resources, designHeight, false);
//    }

    /**
     * Adapt for the vertical screen, and call it in {@link Activity#getResources()}.
     */
//    public static Resources adaptHeight(final Resources resources, final int designHeight, final boolean includeNavBar) {
//        float screenHeight = (resources.getDisplayMetrics().heightPixels
//                + (includeNavBar ? getNavBarHeight(resources) : 0)) * 72f;
//        float newXdpi = screenHeight / designHeight;
//        applyDisplayMetrics(resources, newXdpi);
//        return resources;
//    }

    private static int getNavBarHeight(final Resources resources) {
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return resources.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }
//
//    /**
//     * @param resources The resources.
//     * @return the resource
//     */
//    public static Resources closeAdapt(final Resources resources) {
//        float newXdpi = Resources.getSystem().getDisplayMetrics().density * 72f;
//        applyDisplayMetrics(resources, newXdpi);
//        return resources;
//    }

    /**
     * Value of pt to value of px.
     *
     * @param ptValue The value of pt.
     * @return value of px
     */
//    public static int pt2Px(final float ptValue) {
//        DisplayMetrics metrics = CommonUtils.getApp().getResources().getDisplayMetrics();
//        return (int) (ptValue * metrics.xdpi / 72f + 0.5);
//    }

    /**
     * Value of px to value of pt.
     *
     * @return value of pt
     */
//    public static int px2Pt(final float pxValue) {
//        DisplayMetrics metrics = CommonUtils.getApp().getResources().getDisplayMetrics();
//        return (int) (pxValue * 72 / metrics.xdpi + 0.5);
//    }
//
//    private static void applyDisplayMetrics(final Resources resources, final float newXdpi) {
//        resources.getDisplayMetrics().xdpi = newXdpi;
//        CommonUtils.getApp().getResources().getDisplayMetrics().xdpi = newXdpi;
//        applyOtherDisplayMetrics(resources, newXdpi);
//    }

//    static void preLoad() {
//        applyDisplayMetrics(Resources.getSystem(), Resources.getSystem().getDisplayMetrics().xdpi);
//    }
//
//    private static void applyOtherDisplayMetrics(final Resources resources, final float newXdpi) {
//        if (sMetricsFields == null) {
//            sMetricsFields = new ArrayList<>();
//            Class resCls = resources.getClass();
//            Field[] declaredFields = resCls.getDeclaredFields();
//            while (declaredFields != null && declaredFields.length > 0) {
//                for (Field field : declaredFields) {
//                    if (field.getType().isAssignableFrom(DisplayMetrics.class)) {
//                        field.setAccessible(true);
//                        DisplayMetrics tmpDm = getMetricsFromField(resources, field);
//                        if (tmpDm != null) {
//                            sMetricsFields.add(field);
//                            tmpDm.xdpi = newXdpi;
//                        }
//                    }
//                }
//                resCls = resCls.getSuperclass();
//                if (resCls != null) {
//                    declaredFields = resCls.getDeclaredFields();
//                } else {
//                    break;
//                }
//            }
//        } else {
//            applyMetricsFields(resources, newXdpi);
//        }
//    }
//
//    private static void applyMetricsFields(final Resources resources, final float newXdpi) {
//        for (Field metricsField : sMetricsFields) {
//            try {
//                DisplayMetrics dm = (DisplayMetrics) metricsField.get(resources);
//                if (dm != null) dm.xdpi = newXdpi;
//            } catch (Exception e) {
//                Log.e("AdaptScreenUtils", "applyMetricsFields: " + e);
//            }
//        }
//    }

    private static DisplayMetrics getMetricsFromField(final Resources resources, final Field field) {
        try {
            return (DisplayMetrics) field.get(resources);
        } catch (Exception e) {
            Log.e("AdaptScreenUtils", "getMetricsFromField: " + e);
            return null;
        }
    }


}
