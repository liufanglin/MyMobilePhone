package com.shopex.phone.phone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.activity.AccountPersonActivity;
import com.shopex.phone.phone.activity.LoginActivity;
import com.shopex.phone.phone.activity.SlideSettingsActivity;
import com.shopex.phone.phone.activity.WebActivity;

/**
 * Created by samsung on 2016/3/8.
 * 侧滑页面
 */
public class SlideLeftFragment extends BaseFragment{

    private ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_left, null);
        lv=(ListView) view.findViewById(R.id.listView1);
        String[] strs={"登录","防盗说明","个人中心","设置"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strs);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                }
                if (position == 1) {
                    Intent intent =new Intent(getActivity(), WebActivity.class);
                    startActivity(intent);
                }
                if (position == 2) {
                    Intent intent = new Intent(getActivity(), AccountPersonActivity.class);
                    startActivity(intent);
                }

                if (position == 3) {
                    Intent intent = new Intent(getActivity(), SlideSettingsActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
