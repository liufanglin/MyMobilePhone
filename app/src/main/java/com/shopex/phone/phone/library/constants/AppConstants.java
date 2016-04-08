package com.shopex.phone.phone.library.constants;

import com.shopex.phone.phone.R;

/**
 * Created by samsung on 2016/1/6.
 * 一些全局的信息
 *
 */
public class AppConstants {
    public static boolean isLogin=false;
    public static String TAG="liufanglin-----------";
    public static String PREFERENCE_NAME="phone";
    public static boolean DEBUG=true;//项目是否是调试
    //骚扰拦截里面设置viewpager的标题
    public static String title[]=new String[]{"短信拦截","来电显示"};

    public static int SPLASH=R.mipmap.splash;

    //设置引导页的图片
    public static final int [] GUIDE_PAGE=new int[]{
        R.mipmap.guide1,R.mipmap.guide2,R.mipmap.guide3};
    public static final int [] GUIDE_PAGE_POTION=new int[]{
            R.mipmap.point_default,R.mipmap.poing_active};
    //首页gridview的文字和图片
    public static final int[] MAIN_IMAGE=new int[]{R.mipmap.ck,R.mipmap.ck,R.mipmap.ck,R.mipmap.ck,
            R.mipmap.ck,R.mipmap.ck};
    public static final String[] MAIN_TEXTTITLE=new String[]{"手机防盗","流量监控"
    ,"骚扰拦截","支付保镖","软件管理","其他功能"};
    public static final String[] MAIN_TEXT=new String[]{
            "剩余存储空间85%","看流量去哪了","骚扰拦截","获取10万赔付金","1款软件可以优化","三重引擎防护中"
    };
    //数据库的名字(联系人数据库)
    public static final String DB_NAME_CONTRACT = "contract.db";
    //短信数据库
    public static final String DB_NAME_SMS="sms.db";
    //用户数据库
    public static final String DB_NAME_USER="user.db";

    //表名
    public static final String TABLE="contract_info";
    public static final String DB_NAME="name.db";
    public static final String TABLE_SMS="sms_info";
    public static final String TABLE_USER="user_info";

    //聚合数据appkey
    public static final String PhoneAPPKEY="1c437a472475395778ecdbd549defdb3";

    //用户名
    public static String name;


}
