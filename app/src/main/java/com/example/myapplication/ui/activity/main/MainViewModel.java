package com.example.myapplication.ui.activity.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.common.Code;
import com.example.myapplication.http.bean.Integral;
import com.example.myapplication.http.bean.LoginBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;
import com.orhanobut.hawk.Hawk;

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
    public MutableLiveData<Integral> mIntegral;

    public MutableLiveData<String> userName;
    public MutableLiveData<String> userPwd;

    public MainViewModel() {
        if (userBean == null) {
            userBean = new MutableLiveData<>();
        }
        userHeader = new MutableLiveData<>();
        mIntegral = new MutableLiveData<>();
        userName = new MutableLiveData<>();
        userPwd = new MutableLiveData<>();
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    public LiveData<HttpBaseResponse<LoginBean>> getUserBean() {
        return userBean;
    }

    public LiveData<String> getUserHeader() {
        return userHeader;
    }

    /**
     * 更新当前用户头像
     *
     * @param path
     */
    public void updateUserHeader(String path) {
        userHeader.postValue(path);
        Hawk.put(Code.HawkCode.HEADER_IMAGE + userBean.getValue().data.getUsername(),
                path);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        getUserData();
    }

    /**
     * 获取缓存的用户信息
     */
    private void getUserData() {
        LoginBean loginBean = Hawk.get(Code.HawkCode.LOGIN_DATA);

        if (loginBean != null) {
            login(loginBean.getUsername(), loginBean.getPassword(), true);
        } else {
            HttpBaseResponse bean = new HttpBaseResponse();
            bean.errorCode = 3;
            userBean.postValue(bean);
        }
    }

    /**
     * 用户点击登陆
     */
    public void login() {

        if (CommonUtils.isStringEmpty(userName.getValue()) || CommonUtils.isStringEmpty(userPwd.getValue())) {
            HttpBaseResponse bean = new HttpBaseResponse();
            bean.errorCode = 1;
            userBean.postValue(bean);
            return;
        }
        login(userName.getValue(), userPwd.getValue(), false);
    }

    /**
     * 登陆
     *
     * @param name
     * @param pwd
     */
    private void login(String name, String pwd, boolean autoLogin) {

        HttpRequest.getInstance()
                .Login(name, pwd)
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<LoginBean>>() {
                    @Override
                    public void success(HttpBaseResponse<LoginBean> bean) {
                        if (autoLogin) {
                            if (bean.errorCode == 0) {
                                userBean.postValue(bean);
                                loadUserHeader();
                            } else {
                                userName.postValue(name);
                            }
                        } else {
                            userBean.postValue(bean);
                            if (bean.errorCode == 0) {
                                loadUserHeader();
                            }
                        }
                        bean.data.setPassword(pwd);
                        Hawk.put(Code.HawkCode.LOGIN_DATA, bean.data);
                    }
                });
    }

    /**
     * 从本地缓存中获取用户头像
     */
    private void loadUserHeader() {
        String path = Hawk.get(Code.HawkCode.HEADER_IMAGE + userBean.getValue().data.getUsername());
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
                .subscribe(new HttpDisposable<HttpBaseResponse<Integral>>() {
                    @Override
                    public void success(HttpBaseResponse<Integral> integral) {

                        if (integral.errorCode == 0) {
                            mIntegral.postValue(integral.data);
                        }
                    }
                });
    }

}
