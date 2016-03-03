package com.shopex.phone.phone.bean;

import java.io.Serializable;

public class SortModel implements Serializable{

	private String name;   //名字
	private String sortLetters;  //拼音首字母
	private String phone;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public String getPhone(){return phone;}
	public void setPhone(String phone){
		this.phone=phone;
	}
}
