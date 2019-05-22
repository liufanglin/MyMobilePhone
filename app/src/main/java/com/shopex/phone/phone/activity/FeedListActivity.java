package com.shopex.phone.phone.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.adapter.FeedAdapter;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufanglin on 2019/5/22.
 */

public class FeedListActivity extends BaseActivity {

    public RecyclerView recycleview1;
    public RecyclerView recycleview2;
    public Button btn_add;
    public EditText et_food;
    public List<String> leftList=new ArrayList<>();
    public FeedAdapter leftAdapter,rightAdapter;
    public List<String> rightList=new ArrayList<>();
    public String selectType="饮料";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setCenterTitle("feed");
        recycleview1= (RecyclerView) findViewById(R.id.recycleview1);
        recycleview2= (RecyclerView) findViewById(R.id.recycleview2);
        btn_add= (Button) findViewById(R.id.btn_add);
        et_food= (EditText) findViewById(R.id.et_food);
        leftList.add("饮料");
        leftList.add("膳食");
        leftList.add("肉类");
        leftList.add("零食");
        leftList.add("面包");
        leftList.add("蛋糕");
        leftList.add("水果");
        leftList.add("蔬菜");
        leftList.add("其他");
        leftAdapter=new FeedAdapter(FeedListActivity.this,leftList);
        recycleview1.setAdapter(leftAdapter);
        recycleview1.setLayoutManager(new LinearLayoutManager(FeedListActivity.this));
        leftAdapter.setListern(new FeedAdapter.OnClistListern() {
            @Override
            public void result(String feed) {
                selectType=feed;
                unpdateList();

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=et_food.getText().toString();
                if (!TextUtils.isEmpty(content)){
                    addList(content,selectType);
                    unpdateList();

                }
            }
        });

        rightAdapter=new FeedAdapter(FeedListActivity.this,rightList);
        recycleview2.setAdapter(rightAdapter);
        recycleview2.setLayoutManager(new LinearLayoutManager(FeedListActivity.this));
        recycleview2.setAdapter(rightAdapter);
        unpdateList();


    }


    public void unpdateList(){
        rightList.clear();
        JSONArray jsonArray=getList(selectType);
        if (jsonArray!=null&&jsonArray.length()>0){
            for (int i=0;i<jsonArray.length();i++){
                rightList.add(jsonArray.optString(i));
            }
        }
        rightAdapter.notifyDataSetChanged();
    }

    public void addList(String content,String selectType){

        JSONArray jsonArray=new JSONArray();
        JSONArray localArray= getList(selectType);
        if (localArray!=null&&localArray.length()>0){
            for (int i=0;i<localArray.length();i++){
                jsonArray.put(localArray.optString(i));
            }
        }
        jsonArray.put(content);
        PreferencesUtils.setString(FeedListActivity.this,selectType,jsonArray.toString());
    }

    public JSONArray getList(String selectType){
        String local= PreferencesUtils.getString(FeedListActivity.this,selectType);
        try {
            if (TextUtils.isEmpty(local)){
                return null;
            }else {
                JSONArray jsonArray=new JSONArray(local);
                return jsonArray;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
