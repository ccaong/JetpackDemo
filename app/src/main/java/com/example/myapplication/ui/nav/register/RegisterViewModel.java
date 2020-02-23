package com.example.myapplication.ui.nav.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;
import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;

import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends BaseViewModel {

    private MutableLiveData<HttpBaseResponse<Object>> registerStatus;

    public MutableLiveData<String> userName;
    public MutableLiveData<String> userPwd;
    public MutableLiveData<String> userRePwd;

    public RegisterViewModel() {
        registerStatus = new MutableLiveData<>();
        userName = new MutableLiveData<>();
        userPwd = new MutableLiveData<>();
        userRePwd = new MutableLiveData<>();
    }

    public LiveData<HttpBaseResponse<Object>> getRegisterStatus() {
        return registerStatus;
    }

    /**
     * 用户点击登陆
     */
    public void login() {
        if (CommonUtils.isStringEmpty(userName.getValue()) ||
                CommonUtils.isStringEmpty(userPwd.getValue())) {
            return;
        }

        if (userPwd.getValue().equals(userRePwd.getValue())) {
            register(userName.getValue(), userPwd.getValue(), userRePwd.getValue());
        } else {
            HttpBaseResponse<Object> bean = new HttpBaseResponse<>();
            bean.errorCode = 1;
            bean.errorMsg = getResources().getString(R.string.register_error);
            registerStatus.postValue(bean);

            userPwd.postValue("");
            userRePwd.postValue("");
        }
    }

    /**
     * 点击注册
     *
     * @param name
     * @param pwd
     * @param rePwd
     */
    private void register(String name, String pwd, String rePwd) {
        HttpRequest.getInstance()
                .register(name, pwd, rePwd)
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<Object>>() {
                    @Override
                    public void success(HttpBaseResponse<Object> bean) {
                        registerStatus.postValue(bean);
                    }
                });
    }
}
