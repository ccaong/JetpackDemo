package com.example.myapplication.ui.activity.main;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.config.Constants;
import com.example.myapplication.bean.responsebean.Coin;
import com.example.myapplication.bean.responsebean.LoginBean;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;
import com.orhanobut.hawk.Hawk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class MainViewModel extends BaseViewModel {

    /**
     * 登陆用户信息
     */
    private MutableLiveData<LoginBean> userBean;

    /**
     * 用户头像
     */
    private MutableLiveData<String> userHeader;

    /**
     * 积分信息
     */
    public MutableLiveData<Coin> mIntegral;

    public MainViewModel() {
        userBean = new MutableLiveData<>();
        userHeader = new MutableLiveData<>();
        mIntegral = new MutableLiveData<>();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public LiveData<LoginBean> getUserBean() {
        return userBean;
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    public LiveData<String> getUserHeader() {
        return userHeader;
    }

    /**
     * 设置用户信息
     */
    public void setUserBean() {
        //从缓存中读取用户信息
        LoginBean data = Hawk.get(Constants.HawkCode.LOGIN_DATA);
        userBean.postValue(data);

        //读取本地缓存中的用户头像
        loadUserHeader(data.getUsername());
    }

    /**
     * 获取缓存的用户信息
     */
    public void getUserData() {
        LoginBean loginBean = Hawk.get(Constants.HawkCode.LOGIN_DATA);

        if (loginBean != null) {
            //自动登录
            login(loginBean.getUsername(), loginBean.getPassword());
        } else {
            //缓存中没有用户信息
            userBean.postValue(null);
        }
    }

    /**
     * 自动登陆
     *
     * @param name
     * @param pwd
     */
    private void login(String name, String pwd) {

        HttpRequest.getInstance()
                .Login(name, pwd)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<LoginBean>() {
                    @Override
                    public void success(LoginBean bean) {
                        //自动登录成功
                        userBean.postValue(bean);
                        bean.setPassword(pwd);
                        Hawk.put(Constants.HawkCode.LOGIN_DATA, bean);
                        loadUserHeader(bean.getUsername());
                    }
                });
    }

    /**
     * 从本地缓存中获取用户头像
     */
    private void loadUserHeader(String userName) {
        String path = Hawk.get(Constants.HawkCode.HEADER_IMAGE + userName);
        if (!CommonUtils.isStringEmpty(path)) {
            userHeader.postValue(path);
        }
    }


    /**
     * 退出
     */
    public void logout() {

        HttpRequest.getInstance()
                .logout()
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<Object>() {
                    @Override
                    public void success(Object integral) {
                        //清除本地缓存
                        Hawk.delete(Constants.HawkCode.LOGIN_DATA);
                        Hawk.delete(Constants.HawkCode.COOKIE);

                        userBean.postValue(null);

                        userHeader.postValue(null);
                        Coin coin = new Coin();
                        coin.setCoinCount(0);
                        mIntegral.postValue(coin);
                    }
                });

    }

    /**
     * 获取积分
     */
    public void loadMyIntegral() {

        HttpRequest.getInstance()
                .getMyIntegral()
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<Coin>() {
                    @Override
                    public void success(Coin integral) {
                        mIntegral.postValue(integral);
                    }
                });
    }


    /**
     * 更新当前用户头像
     *
     * @param path
     */
    public void updateUserHeader(String path) {
        userHeader.postValue(path);
        Hawk.put(Constants.HawkCode.HEADER_IMAGE + userBean.getValue().getUsername(), path);
    }
}
