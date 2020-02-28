package com.example.myapplication.ui.activity.login;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.common.Code;
import com.example.myapplication.http.bean.LoginBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;
import com.orhanobut.hawk.Hawk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : devel
 * @date : 2020/2/28 14:22
 * @desc :
 */
public class LoginViewModel extends BaseViewModel {

    /**
     * 登陆用户信息
     */
    private MutableLiveData<HttpBaseResponse<LoginBean>> userBean;

    /**
     * 注册状态
     */
    private MutableLiveData<HttpBaseResponse<Object>> registerStatus;

    public MutableLiveData<String> userName;
    public MutableLiveData<String> userPwd;
    public MutableLiveData<String> userRePwd;


    public LoginViewModel() {
        userBean = new MutableLiveData<>();
        registerStatus = new MutableLiveData<>();

        userName = new MutableLiveData<>();
        userPwd = new MutableLiveData<>();
        userRePwd = new MutableLiveData<>();
    }

    public void clearUserPwd() {
        userPwd.postValue("");
        userRePwd.postValue("");
    }

    public LiveData<HttpBaseResponse<LoginBean>> getUserBean() {
        return userBean;
    }

    public LiveData<HttpBaseResponse<Object>> getRegisterStatus() {
        return registerStatus;
    }

    /**
     * 登陆
     */
    public void login() {
        if (CommonUtils.isStringEmpty(userName.getValue()) ||
                CommonUtils.isStringEmpty(userPwd.getValue())) {
            //用户名或密码为空
            return;
        }


        HttpRequest.getInstance()
                .Login(userName.getValue(), userPwd.getValue())
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<LoginBean>>() {
                    @Override
                    public void success(HttpBaseResponse<LoginBean> bean) {
                        // TODO: 2020/2/28 登录成功，保存用户信息
                        userBean.postValue(bean);
                        bean.data.setPassword(userPwd.getValue());
                        Hawk.put(Code.HawkCode.LOGIN_DATA, bean.data);
                    }
                });
    }


    /**
     * 点击注册
     */
    public void register() {
        HttpRequest.getInstance()
                .register(userName.getValue(), userPwd.getValue(), userRePwd.getValue())
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<Object>>() {
                    @Override
                    public void success(HttpBaseResponse<Object> bean) {
                        registerStatus.postValue(bean);
                    }
                });
    }
}
