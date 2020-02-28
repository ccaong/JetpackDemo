package com.example.myapplication.ui.activity.main;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.common.Code;
import com.example.myapplication.http.bean.Coin;
import com.example.myapplication.http.bean.LoginBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;
import com.orhanobut.hawk.Hawk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {

    /**
     * 登陆用户信息
     */
    private MutableLiveData<HttpBaseResponse<LoginBean>> userBean;

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
    public LiveData<HttpBaseResponse<LoginBean>> getUserBean() {
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
        HttpBaseResponse<LoginBean> httpBean = new HttpBaseResponse<>();
        httpBean.errorCode = 0;
        httpBean.data = Hawk.get(Code.HawkCode.LOGIN_DATA);
        userBean.postValue(httpBean);

        //读取本地缓存中的用户头像
        loadUserHeader(httpBean.data.getUsername());
    }

    /**
     * 获取缓存的用户信息
     */
    public void getUserData() {
        LoginBean loginBean = Hawk.get(Code.HawkCode.LOGIN_DATA);

        if (loginBean != null) {
            //自动登录
            login(loginBean.getUsername(), loginBean.getPassword());
        } else {
            //缓存中没有用户信息
            HttpBaseResponse bean = new HttpBaseResponse();
            bean.errorCode = 3;
            userBean.postValue(bean);
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
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<LoginBean>>() {
                    @Override
                    public void success(HttpBaseResponse<LoginBean> bean) {
                        if (bean.errorCode == 0) {
                            //自动登录成功
                            userBean.postValue(bean);
                            bean.data.setPassword(pwd);
                            Hawk.put(Code.HawkCode.LOGIN_DATA, bean.data);
                            loadUserHeader(bean.data.getUsername());
                        }
                    }
                });
    }

    /**
     * 从本地缓存中获取用户头像
     */
    private void loadUserHeader(String userName) {
        String path = Hawk.get(Code.HawkCode.HEADER_IMAGE + userName);
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
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<Object>>() {
                    @Override
                    public void success(HttpBaseResponse<Object> integral) {
                        //清除本地缓存
                        Hawk.delete(Code.HawkCode.LOGIN_DATA);
                        Hawk.delete(Code.HawkCode.COOKIE);

                        HttpBaseResponse bean = new HttpBaseResponse();
                        bean.errorCode = 3;
                        userBean.postValue(bean);

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
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<Coin>>() {
                    @Override
                    public void success(HttpBaseResponse<Coin> integral) {

                        if (integral.errorCode == 0) {
                            mIntegral.postValue(integral.data);
                        }
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
        Hawk.put(Code.HawkCode.HEADER_IMAGE + userBean.getValue().data.getUsername(), path);
    }
}
