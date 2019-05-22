package com.shopex.phone.phone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.library.toolbox.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufanglin on 2019/5/22.
 */

public class FeedAdapter extends RecyclerView.Adapter{

    public Context context;
    public List<String> list=null;
    public OnClistListern listern;
    public interface OnClistListern{
        void result(String feed);
    }



    public FeedAdapter(Context context, List<String> list){
        this.context=context;
        this.list=list;
    }

    public void setListern(OnClistListern listern){
        this.listern=listern;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_feed, null);
        return new ClassViewHodel(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ClassViewHodel classViewHodel = (ClassViewHodel) holder;
        final String text=list.get(position);
        classViewHodel.tv_class.setText(text);
        classViewHodel.tv_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listern!=null){
                    listern.result(text);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }


    class ClassViewHodel extends RecyclerView.ViewHolder {

        public TextView tv_class;
        public ClassViewHodel(View itemView) {
            super(itemView);
            tv_class = (TextView) itemView.findViewById(R.id.tv_class);

        }
    }


}

