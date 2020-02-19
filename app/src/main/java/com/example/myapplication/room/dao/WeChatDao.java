package com.example.myapplication.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.WeChatBean;

import java.util.List;

/**
 * @author : devel
 * @date : 2019/11/18 11:08
 * @desc :
 */
@Dao
public interface WeChatDao {


    /**
     * 获取所有公众号
     *
     * @return
     */
    @Query("Select * from wechat")
    List<WeChatBean> getAll();

    /**
     * 获取指定公众号
     *
     * @param userId
     * @return
     */
    @Query("Select * from wechat Where id == (:userId)")
    List<WeChatBean> getUserById(String userId);


    /**
     * 插入一条数据
     *
     * @param weChatBean
     */
    @Insert
    void insertWeChat(WeChatBean weChatBean);


    /**
     * 插入多条数据
     *
     * @param weChatBeans
     */
    @Insert
    void insertAll(WeChatBean... weChatBeans);


    /**
     * 插入多条数据
     *
     * @param weChatBeans
     */
    @Insert
    void insertList(List<WeChatBean> weChatBeans);


    /**
     * 删除数据
     *
     * @param weChatBeans
     */
    @Delete
    void delete(WeChatBean... weChatBeans);

    /**
     * 删除全部
     */
    @Query("DELETE FROM wechat")
    void deleteAll();


    /**
     * 更新数据
     *
     * @param weChatBeans
     */
    @Update
    void update(WeChatBean... weChatBeans);

    /**
     * 修改某条数据的name属性
     *
     * @param name1
     * @param uid
     */
    @Query("update wechat set name =:name1 where id=:uid")
    void updateCustom(String name1, String uid);
}
