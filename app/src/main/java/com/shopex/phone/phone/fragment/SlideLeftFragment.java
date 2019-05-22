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
import com.shopex.phone.phone.activity.FeedListActivity;
import com.shopex.phone.phone.activity.LoginActivity;
import com.shopex.phone.phone.activity.MapActivity;
import com.shopex.phone.phone.activity.SlideSettingsActivity;
import com.shopex.phone.phone.activity.TuTbaleActivity;
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
        String[] strs={"FEED","STEP","MAP"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strs);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(getContext(), FeedListActivity.class));

                }
                if (position == 1) {
                    startActivity(new Intent(getContext(), TuTbaleActivity.class));

                }
                if (position == 2) {
                    startActivity(new Intent(getContext(), MapActivity.class));

                }


            }
        });

        return view;
    }
}
