package com.shopex.phone.phone.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shopex.phone.phone.R;
import com.shopex.phone.phone.bean.UserInfo;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.db.User;
import com.shopex.phone.phone.library.constants.AppConstants;
import com.shopex.phone.phone.library.db.DbServiceUser;
import com.shopex.phone.phone.library.view.CircleImageView;
import com.shopex.phone.phone.library.view.DialogView;

/**
 * Created by samsung on 2016/4/20
 */
public class AccountPersonActivity  extends BaseActivity{
    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout3;
    private RelativeLayout relativeLayout4;
    private LinearLayout linearLayout;
    private CircleImageView circleImageView;
    private TextView textNick;
    private TextView textSex;
    private TextView textPhone;
    private TextView textSignal;
    private final static int RESPONCODE=1001;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_person);
        setLeftBackImageText("个人中心", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
        initData();

    }
    public void initView(){
        relativeLayout1= (RelativeLayout) findViewById(R.id.rl_set1);
        relativeLayout2= (RelativeLayout) findViewById(R.id.rl_set2);
        relativeLayout3= (RelativeLayout) findViewById(R.id.rl_set3);
        relativeLayout4= (RelativeLayout) findViewById(R.id.rl_set4);
        linearLayout= (LinearLayout) findViewById(R.id.rl_set5);
        circleImageView= (CircleImageView) findViewById(R.id.circle_image);
        textNick= (TextView) findViewById(R.id.tv_nick);
        textPhone= (TextView) findViewById(R.id.tv_phone);
        textSex= (TextView) findViewById(R.id.tv_sex);
        textSignal= (TextView) findViewById(R.id.tv_signal);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AccountPersonActivity.this,EditActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("type","nick");
                intent.putExtras(bundle);
                startActivityForResult(intent, RESPONCODE);
            }
        });
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=DialogView.showAlertDialog(AccountPersonActivity.this, "性别选择", "", "", null, null, true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (v.getId() == R.id.dialog_gender1) {
                            UserInfo.getInstance().photo="男";
                            textSex.setText("男");
                        } else if (v.getId() == R.id.dialog_gender2) {
                            UserInfo.getInstance().photo="女";
                            textSex.setText("女");
                        } else if (v.getId() == R.id.dialog_gender3) {
                            UserInfo.getInstance().photo="其他";
                            textSex.setText("其他");
                        }

                    }
                });
            }
        });
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AccountPersonActivity.this,EditActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("type","signle");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    public void initData(){
        if (AppConstants.isLogin){
            circleImageView.setImageResource(R.mipmap.ccc);
        }else {
            circleImageView.setImageResource(R.mipmap.cc);
        }

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);

    }

    @Override
    protected void onResume() {
        super.onResume();
        textNick.setText(UserInfo.getInstance().nick == null ? "" : UserInfo.getInstance().nick);
        textSignal.setText(UserInfo.getInstance().phone==null ? "":UserInfo.getInstance().phone);
        textSex.setText(UserInfo.getInstance().photo==null ? "":UserInfo.getInstance().photo);
        textPhone.setText(UserInfo.getInstance().account==null ? "":UserInfo.getInstance().account);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DbServiceUser.getInstance(AccountPersonActivity.this).SelectNote(UserInfo.getInstance().account == null ? "" : UserInfo.getInstance().account);



        User user=new User();
        user.setId(System.currentTimeMillis());
        user.setNick(UserInfo.getInstance().nick == null ? "" : UserInfo.getInstance().nick);
        user.setAccount(UserInfo.getInstance().account == null ? "" : UserInfo.getInstance().account);
        user.setPhone(UserInfo.getInstance().phone == null ? "" : UserInfo.getInstance().phone);
        user.setPwd(UserInfo.getInstance().pwd == null ? "" : UserInfo.getInstance().pwd);
        user.setPhoto(UserInfo.getInstance().photo == null ? "" : UserInfo.getInstance().photo);
        DbServiceUser.getInstance(AccountPersonActivity.this).insert(user);


    }
}
