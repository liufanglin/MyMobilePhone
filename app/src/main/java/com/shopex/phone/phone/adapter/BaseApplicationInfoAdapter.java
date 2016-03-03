package com.shopex.phone.phone.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.bean.AppInfo;

import java.util.List;

/**
 * Created by samsung on 2016/2/17.
 */
public class BaseApplicationInfoAdapter extends BaseAdapter{
    private List<AppInfo> list;
    private Context context;
    private LayoutInflater inflater=null;
    private boolean isLocalApp=true;
    public BaseApplicationInfoAdapter(Context context,List<AppInfo> list,boolean isLocalApp){
        this.list=list;
        this.context=context;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.isLocalApp=isLocalApp;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=null;
        ViewHolder holder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.activity_allapp_list,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.appicon.setImageDrawable(list.get(position).getIcon());
        holder.tvPackageName.setText(list.get(position).getName());
        holder.tvAppLable.setText(list.get(position).getPackname());
        holder.btnStop.setText("卸载");
        holder.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLocalApp) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + list.get(position).getPackname()));
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context,"系统应用需先获取Root权限",3000).show();
                }
            }
        });

        return convertView;
    }
    class ViewHolder{
        ImageView appicon;
        TextView tvAppLable;
        TextView tvPackageName;
        Button btnStop;
        public ViewHolder(View view){
            this.appicon= (ImageView) view.findViewById(R.id.iv_icon);
            this.tvAppLable= (TextView) view.findViewById(R.id.tv_lable);
            this.tvPackageName= (TextView) view.findViewById(R.id.tv_packag);
            this.btnStop= (Button) view.findViewById(R.id.btn_stop);
        }
    }
}
