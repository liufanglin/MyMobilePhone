package com.shopex.phone.phone.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.bean.Sms;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.library.constants.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsung on 2016/1/9.
 * 短信拦截
 */
public class InterEmsFramgent extends BaseFragment {
    private LinearLayout linearLayout=null;
    private RecyclerView recyclerView=null;
    private Button clear=null;
    private Button more=null;
    private List<Sms> smsData=null;
    private MyAdapter adapter=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout= (RelativeLayout) inflater.inflate(R.layout.fragment_ems,null);
        linearLayout= (LinearLayout) layout.findViewById(R.id.sms_center);
        recyclerView= (RecyclerView) layout.findViewById(R.id.mylistsms);
        clear= (Button) layout.findViewById(R.id.btn_cancel);
        more= (Button) layout.findViewById(R.id.btn_more);
        smsData=selectSms();
        if (smsData.size()!=0){
            linearLayout.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter=new MyAdapter();
            recyclerView.setAdapter(adapter);
        }
        return layout;
    }
/**
 * 查询短信数据库里的数据
 * name,phone,content,time
 */
    public List<Sms> selectSms(){
        List<Sms> lists=new ArrayList<>();
        Cursor cursor= BaseApplication.smsDb.query(AppConstants.TABLE_SMS,new String[]{"phone","content","time",}, null, null, null, null, null) ;
        while(cursor.moveToNext()){
            Sms sms=new Sms();
            String phone=cursor.getString(0);
            String content=cursor.getString(1);
            String time=cursor.getString(2);
            sms.setDate(time);
            sms.setPhone(phone);
            sms.setContent(content);
            lists.add(sms);
        }

        return lists;

    }
    public interface OnItemClickEventListener{
        void onItemClick(int postion);
        void onItemLongClick(int postion);
    }
    public class MyAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sms_item,null);

            return new MyViewholder(view, new OnItemClickEventListener() {
                @Override
                public void onItemClick(int postion) {

                }

                @Override
                public void onItemLongClick(int postion) {

                }
            });
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewholder mholder= (MyViewholder) holder;
            mholder.phone.setText(smsData.get(position).getPhone());
            mholder.time.setText(smsData.get(position).getDate());
            mholder.content.setText(smsData.get(position).getContent());

        }

        @Override
        public int getItemCount() {
            return smsData.size();
        }
        public class MyViewholder extends RecyclerView.ViewHolder{
            public TextView phone=null;
            public TextView time=null;
            public TextView content=null;
            public MyViewholder(View itemView,final OnItemClickEventListener lis) {
                super(itemView);
                phone= (TextView) itemView.findViewById(R.id.phone);
                time= (TextView) itemView.findViewById(R.id.time);
                content= (TextView) itemView.findViewById(R.id.content);
                if (null!=lis){
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lis.onItemClick(getAdapterPosition());
                        }
                    });
                    itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            lis.onItemLongClick(getAdapterPosition());
                            return true;
                        }
                    });
                }
            }
        }

    }

}
