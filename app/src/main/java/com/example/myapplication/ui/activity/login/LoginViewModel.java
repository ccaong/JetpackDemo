package com.example.myapplication.ui.activity.login;

import android.widget.Toast;

import com.example.myapplication.config.App;
import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.config.Constants;
import com.example.myapplication.bean.responsebean.LoginBean;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;
import com.orhanobut.hawk.Hawk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author : devel
 * @date : 2020/2/28 14:22
 * @desc :
 */
public class LoginViewModel extends BaseViewModel {

    /**
     * 登陆用户信息
     */
    private MutableLiveData<LoginBean> userBean;

    /**
     * 注册状态
     */
    private MutableLiveData<Object> registerStatus;

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

    public LiveData<LoginBean> getUserBean() {
        return userBean;
    }

    public LiveData<Object> getRegisterStatus() {
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
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<LoginBean>() {
                    @Override
                    public void success(LoginBean bean) {
                        //登录成功，保存用户信息
                        userBean.postValue(bean);
                        bean.setPassword(userPwd.getValue());
                        Hawk.put(Constants.HawkCode.LOGIN_DATA, bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(App.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 点击注册
     */
    public void register() {
        HttpRequest.getInstance()
                .register(userName.getValue(), userPwd.getValue(), userRePwd.getValue())
                .compose(HttpFactory.<Object>schedulers())
                .subscribe(new HttpDisposable<Object>() {
                    @Override
                    public void success(Object bean) {
                        registerStatus.postValue(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        clearUserPwd();
                        Toast.makeText(App.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
