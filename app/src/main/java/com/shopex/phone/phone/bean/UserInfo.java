package com.shopex.phone.phone.bean;

/**
 * Created by samsung on 2016/4/20.
 */
public class UserInfo {
    public static UserInfo instance;
    public String account;//用户名手机号
    public String pwd;//密码
    public String photo;//性别
    public String nick;//昵称
    public String phone;//个性签名
    public static UserInfo getInstance(){
        if (instance==null){
            instance=new UserInfo();
        }
        return instance;
    }
    public UserInfo(){}
    public UserInfo(String account,String pwd,String photo,String nick,String phone){
        this.account=account;
        this.pwd=pwd;
        this.photo=photo;
        this.nick=nick;
        this.phone=phone;
    }

}
