package com.shopex.phone.phone.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.shopex.phone.phone.R;
import com.shopex.phone.phone.adapter.SortAdapter;
import com.shopex.phone.phone.bean.Contract;
import com.shopex.phone.phone.bean.SortModel;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.pinying.CharacterParser;
import com.shopex.phone.phone.library.pinying.PinyinComparator;
import com.shopex.phone.phone.library.toolbox.LogUtils;
import com.shopex.phone.phone.library.view.ClearEditText;
import com.shopex.phone.phone.library.view.SideBar;
import com.shopex.phone.phone.utils.ReadFriend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.dao.InternalUnitTestDaoAccess;

/**
 * Created by samsung on 2016/1/13.
 * 读取本地联系人
 */
public class LocalPhoneActivity extends BaseActivity {

    private ClearEditText mClearEditText;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private Intent intent;
    public static final int FORPHONERESULT=1;


    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_phone);
        setLeftBackImageText("从联系人添加", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViews();
    }

    private void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getApplication(), ((SortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();

                BaseApplication.db.execSQL("insert into contract_info(name, phone) values(?, ?)",
                        new Object[]{SourceDateList.get(position).getName(),SourceDateList.get(position).getPhone()});
                intent=getIntent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("item",SourceDateList.get(position));
                intent.putExtras(bundle);
                setResult(FORPHONERESULT,intent);
                finish();

            }
        });

        List<Contract> list= ReadFriend.curesorContract(LocalPhoneActivity.this);

        SourceDateList = filledData(list);

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);


        sortListView.setAdapter(adapter);


        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * 为ListView填充数据
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<Contract> date){
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for(int i=0; i<date.size(); i++){
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).getName());
            sortModel.setPhone(date.get(i).getPhone());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                sortModel.setSortLetters(sortString.toUpperCase());
            }else{
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }

        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr){
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if(TextUtils.isEmpty(filterStr)){
            filterDateList = SourceDateList;
        }else{
            filterDateList.clear();
            for(SortModel sortModel : SourceDateList){
                String name = sortModel.getName();
                if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
