package com.shopex.phone.phone.bean;

import android.graphics.drawable.Drawable;


public class AppInfo {
	
	private String apkpath;
	
	
	public String getApkpath() {
		return apkpath;
	}

	public void setApkpath(String apkpath) {
		this.apkpath = apkpath;
	}

	private Drawable icon;
	private String name;
	private boolean inRom;
	private long appSize;
	private boolean userApp;
	private String packname;

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInRom() {
		return inRom;
	}

	public void setInRom(boolean inRom) {
		this.inRom = inRom;
	}

	public long getAppSize() {
		return appSize;
	}

	public void setAppSize(long appSize) {
		this.appSize = appSize;
	}

	public boolean isUserApp() {
		return userApp;
	}

	public void setUserApp(boolean userApp) {
		this.userApp = userApp;
	}

	public String getPackname() {
		return packname;
	}

	public void setPackname(String packname) {
		this.packname = packname;
	}

	@Override
	public String toString() {
		return "AppInfo [name=" + name + ", inRom=" + inRom + ", appSize="
				+ appSize + ", userApp=" + userApp + ", packname=" + packname
				+ "]";
	}
}
