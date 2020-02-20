package com.example.myapplication.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.entity.WeChatBean;
import com.example.myapplication.room.dao.WeChatDao;

/**
 * @author : devel
 * @date : 2019/11/18 11:04
 * @desc :
 */

@Database(entities = {WeChatBean.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    /**
     * 获取公众号DAO
     *
     * @return
     */
    public abstract WeChatDao getWeChatDao();


    private static final String DB_NAME = "room_test";

    private static volatile AppDataBase appDataBase;

    public static AppDataBase getInstance(Context context) {
        if (appDataBase == null) {
            synchronized (AppDataBase.class) {
                if (appDataBase == null) {
                    appDataBase =  Room.databaseBuilder(context, AppDataBase.class, DB_NAME)
                            .build();
                }
            }
        }
        return appDataBase;
    }
}
