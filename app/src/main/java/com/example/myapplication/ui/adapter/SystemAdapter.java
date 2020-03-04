package com.example.myapplication.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.bean.responsebean.WeChatBean;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author devel
 */
public class SystemAdapter extends RecyclerView.Adapter<SystemAdapter.SystemViewHolder> {

    public List<WeChatBean> systemList;

    public SystemAdapter(List<WeChatBean> list) {
        this.systemList = list;
    }

    /**
     * 改变数据
     *
     * @param newItemDatas
     */
    public void onItemDatasChanged(List<WeChatBean> newItemDatas) {
        this.systemList = newItemDatas;
        notifyDataSetChanged();
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

        WeChatBean bean = systemList.get(position);
        //绑定数据
        holder.binding.setVariable(BR.systemData, bean);
        showTagView(holder.binding.getRoot().findViewById(R.id.children_view), bean.getChildren(), holder);
        addListener(holder.binding.getRoot(), bean, position);
        //防止数据闪烁
        holder.binding.executePendingBindings();
    }


    private void showTagView(RecyclerView recyclerView, final List<WeChatBean> beanList, SystemViewHolder holder) {

        FlexboxLayoutManager manager = new FlexboxLayoutManager(holder.binding.getRoot().getContext());
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setFlexWrap(FlexWrap.WRAP);
        recyclerView.setLayoutManager(manager);
        CommonAdapter<WeChatBean> commonAdapter = new CommonAdapter<WeChatBean>(beanList, R.layout.item_system_tag_layout, BR.systemChildren) {
            @Override
            public void addListener(View root, WeChatBean itemData, int position) {
                root.findViewById(R.id.tv_tag).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addChildrenListener(root, itemData, beanList,position);
                    }
                });
            }
        };
        recyclerView.setAdapter(commonAdapter);
    }


    /**
     * 添加监听回调
     *
     * @param root
     * @param itemData
     * @param position
     */
    public void addListener(View root, WeChatBean itemData, int position) {
    }

    /**
     * 添加监听回调
     *
     * @param root
     * @param itemData
     * @param parentSystem
     */
    public void addChildrenListener(View root, WeChatBean itemData, List<WeChatBean> parentSystem,int position) {
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
