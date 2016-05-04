package com.shopex.phone.phone.library.view;

import java.io.Serializable;


public class ChartData implements Serializable{
    public String day;
    public String users;
    public String orders;

    public String sales;


    public double getMoney(){
        return Double.parseDouble(sales);
    }

    public  String getMyData(){
        return day;
    }

    @Override
    public String toString() {
        return "ChartData{" +
                "day='" + day + '\'' +
                ", users='" + users + '\'' +
                ", orders='" + orders + '\'' +
                ", money=" + getMoney() +
                ", sales='" + sales + '\'' +
                '}';
    }
}

