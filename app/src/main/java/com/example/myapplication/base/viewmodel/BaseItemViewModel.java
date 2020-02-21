package com.example.myapplication.base.viewmodel;


import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

/**
 * Item的ViewModel的基类
 *
 * @author devel
 * @param <T> 基础model的类型
 */
public abstract class BaseItemViewModel<T> extends BaseObservable {

    public final ObservableField<T> baseModel = new ObservableField<>();

    /**
     * 获取基础model
     *
     * @return 基础model
     */
    public T getBaseModel() {
        return baseModel.get();
    }

    /**
     * 设置基础model
     *
     * @param t 基础model
     */
    public void setBaseModel(T t) {
        baseModel.set(t);

        if (t != null) {
            setAllModel(t);
        }
    }

    /**
     * 设置所有的model。如果设置了基础model，那么会设置所有的model
     *
     * @param t 基础model
     */
    protected abstract void setAllModel(@NonNull T t);
}
