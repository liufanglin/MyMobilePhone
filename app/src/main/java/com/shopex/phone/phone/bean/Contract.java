package com.shopex.phone.phone.bean;

/**
 * Created by samsung on 2016/1/12.
 * 联系人
 */
public class Contract {
    private String name;
    private String phone;
    public Contract(){}
    public Contract(String name,String phone){
        this.name=name;
        this.phone=phone;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }
    public String getPhone(){
        return phone;
    }



}
