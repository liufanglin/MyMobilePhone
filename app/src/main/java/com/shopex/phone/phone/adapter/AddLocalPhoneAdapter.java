package com.shopex.phone.phone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.shopex.phone.phone.bean.Contract;


/**
 * Created by samsung on 2016/1/13.
 * 本地联系人
 */
public class AddLocalPhoneAdapter extends RecyclerView.Adapter{
    public Context context;
    public AddLocalPhoneAdapter(Context context){
        this.context=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
