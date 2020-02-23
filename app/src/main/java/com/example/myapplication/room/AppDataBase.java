package com.example.myapplication.room;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @author : devel
 * @date : 2019/11/18 11:04
 * @desc :
 */

//@Database(entities = {WeChatBean.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    /**
     *
     *
     * @return
     */


    private static final String DB_NAME = "room_test";

    private static volatile AppDataBase appDataBase;

    public static AppDataBase getInstance(Context context) {
        if (appDataBase == null) {
            synchronized (AppDataBase.class) {
                if (appDataBase == null) {
                    appDataBase = Room.databaseBuilder(context, AppDataBase.class, DB_NAME)
                            .build();
                }
            }
        }
        return appDataBase;
    }
}
