package com.example.myapplication.ui.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.http.bean.System;
import com.google.android.material.internal.FlowLayout;

import java.util.List;

public class SystemAdapter extends RecyclerView.Adapter<SystemAdapter.SystemViewHolder> {

    public List<System> systemList;

    public SystemAdapter(List<System> list) {
        this.systemList = list;
    }

    @NonNull
    @Override
    public SystemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.
                inflate(LayoutInflater.from(parent.getContext()), R.layout.item_system_layout, parent, false);
        return new SystemViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SystemViewHolder holder, int position) {

        System bean = systemList.get(position);
        //绑定数据
        holder.binding.setVariable(BR.systemData, bean);
        addListener(holder.binding.getRoot(), bean, position);
        //防止数据闪烁
        holder.binding.executePendingBindings();

//        holder.binding.getRoot().findViewById(R.id.)
        bean.getChildren();
    }


//    private void showTagView(FlowLayout flowLayout, final List<System> beanList) {
//        flowLayout.removeAllViews();
//        for (int i = 0; i < beanList.size(); i++) {
//            TextView textView = (TextView) View.inflate(flowLayout.getContext(), R.layout.layout_navi_tag, null);
//            textView.setText();
//            flowLayout.addView(textView);
//        });
//    }


    /**
     * 添加监听回调
     *
     * @param root
     * @param itemData
     * @param position
     */
    public void addListener(View root, System itemData, int position) {
    }

    @Override
    public int getItemCount() {
        return systemList != null ? systemList.size() : 0;
    }


    public class SystemViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public SystemViewHolder(@NonNull ViewDataBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

        }
    }
}
