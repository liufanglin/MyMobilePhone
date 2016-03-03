package com.shopex.phone.phone.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.shopex.phone.phone.R;
import com.shopex.phone.phone.bean.Contract;
import com.shopex.phone.phone.bean.SortModel;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.toolbox.ScreenUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by samsung on 2016/1/12.
 * 这是添加黑名单的逻辑
 */
public class AddBlankListActivity extends BaseActivity{
    private RecyclerView mRecyclerView=null;
    private MyAdapter adapter;
    private List<Contract> list=new ArrayList<>();
    private PopupWindow window;
    private TextView input;
    private TextView add;
    //手动添加给名单页面
    public static final int INPUTSTATUS=0;
    //从联系人中添加黑名单页面
    public static final int ADDLOCALSTATUS=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_list);
        setLeftBackImageText("添加黑名单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //查询数据库
        list=select();



        setRightImage(R.mipmap.add_software, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != window && window.isShowing()) {
                    window.dismiss();
                }
                View menu = AddBlankListActivity.this.getLayoutInflater().inflate(R.layout.popuwindows_add, null);
                input = (TextView) menu.findViewById(R.id.tv_input);
                add = (TextView) menu.findViewById(R.id.tv_add);
                input.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(AddBlankListActivity.this,InputPhoneActivity.class);
                        startActivityForResult(intent,INPUTSTATUS);
                        window.dismiss();
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent(AddBlankListActivity.this,LocalPhoneActivity.class);
                        startActivityForResult(intent,ADDLOCALSTATUS);
                        window.dismiss();
                    }
                });
                window = new PopupWindow(menu, (int) ScreenUtils.dpToPx(AddBlankListActivity.this, 100), (int) ScreenUtils.dpToPx(AddBlankListActivity.this, 80));
                window.setFocusable(true);
                window.setTouchable(true);
                window.setOutsideTouchable(true);
                window.showAsDropDown(v);
                ((LinearLayout) menu).setFocusable(true);
                ((LinearLayout) menu).setFocusableInTouchMode(true);
                menu.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        window.dismiss();
                        return false;
                    }
                });
                menu.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            switch (keyCode) {
                                case KeyEvent.KEYCODE_BACK:
                                    window.dismiss();
                                    break;
                                case KeyEvent.KEYCODE_MENU:
                                    window.dismiss();
                                    break;
                            }
                        }
                        return true;
                    }
                });


            }
        });
        mRecyclerView= (RecyclerView) findViewById(R.id.mylist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(AddBlankListActivity.this));

        adapter=new MyAdapter();
        mRecyclerView.setAdapter(adapter);

    }
    //recycleview是适配器
    public class MyAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(AddBlankListActivity.this).inflate(R.layout.activity_addblank_item,null);
            return new MyViewHolder(view, new OnItemClickListener() {
                @Override
                public void onItemClick(int postion) {
                    BaseApplication.db.delete(AppConstants.TABLE,"name=?",new String[]{list.get(postion).getName()});
                    list.remove(postion);
                    adapter.notifyDataSetChanged();

                }

            });
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder mHolder= (MyViewHolder) holder;
            mHolder.name.setText(list.get(position).getName());
            mHolder.phone.setText(list.get(position).getPhone());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
    //recycled的监听器
    public interface OnItemClickListener{
        void onItemClick(int postion);
    }
    //viewholder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name=null;
        private TextView phone=null;
        private Button remove=null;

        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            remove = (Button) itemView.findViewById(R.id.remove);
            if (null !=listener) {
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(getAdapterPosition());

                    }
                });
            }
        }

    }

    //返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADDLOCALSTATUS&&resultCode==LocalPhoneActivity.FORPHONERESULT){
            SortModel sortModel= (SortModel) data.getExtras().getSerializable("item");
            Contract contract=new Contract();
            contract.setName(sortModel.getName());
            contract.setPhone(sortModel.getPhone());
            list.add(contract);
            adapter.notifyDataSetChanged();

        }if (requestCode==INPUTSTATUS&&InputPhoneActivity.RESULT_INPUT==resultCode){
            String name=data.getStringExtra("name");
            String phone=data.getStringExtra("phone");
            Contract contract=new Contract();
            contract.setName(name);
            contract.setPhone(phone);
            list.add(contract);
            adapter.notifyDataSetChanged();
            Toast.makeText(AddBlankListActivity.this, "1234"+ name, Toast.LENGTH_SHORT).show();

        }
    }
    //数据库查询
    public List<Contract> select(){
        List<Contract> lists=new ArrayList<>();
        Cursor cursor= BaseApplication.db.query(AppConstants.TABLE,new String[]{"name","phone"}, null, null, null, null, null) ;
        while(cursor.moveToNext()){
            Contract contract=new Contract();
            String name=cursor.getString(0);
            String phone=cursor.getString(1);
            contract.setName(name);
            contract.setPhone(phone);
            lists.add(contract);
        }

        return lists;
    }

}
