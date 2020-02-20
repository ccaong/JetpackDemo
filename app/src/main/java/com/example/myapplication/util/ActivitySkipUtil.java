package com.example.myapplication.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author devel
 */
public class ActivitySkipUtil {

    /**
     * 功能描述:简单地Activity的跳转(不携带任何数据)
     *
     * @param activity 发起跳转的Activity实例
     * @param cls      目标Activity实例
     */
    public static void skipAnotherActivity(Activity activity,
                                           Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    /**
     * 功能描述：带数据的Activity之间的跳转
     *
     * @param activity
     * @param cls
     * @param bundle
     */
    public static void skipAnotherActivity(Activity activity,
                                           Class<? extends Activity> cls,
                                           Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

}
