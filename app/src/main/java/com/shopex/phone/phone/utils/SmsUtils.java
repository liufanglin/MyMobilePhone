package com.shopex.phone.phone.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.xmlpull.v1.XmlSerializer;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Xml;
import android.widget.ProgressBar;

import com.shopex.phone.phone.common.BaseApplication;

//备份短信

public class SmsUtils {

	public interface BackUpCallBackSms{
		
		public void befor(int count);
		
		public void onBackUpSms(int process);
		
	}
	
	
	public static boolean backUp(Context context, BackUpCallBackSms callback) {

		// 判断内存卡是否存在
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
		//或许内容提供者
			ContentResolver resolver = context.getContentResolver();
			Uri uri = Uri.parse("content://sms/");
			// type = 1 接收短信
			// type = 2 发送短信
			// cursor 表示游标的意思
			Cursor cursor = resolver.query(uri, new String[]{"address",
					"date", "type", "body"}, null, null, null);
			//短信总数
			int count = cursor.getCount();
//			pd.setMax(count);
//			progressBar1.setMax(count);
			
			callback.befor(count);
			int process = 0;
			while (cursor.moveToNext()){
				String address=cursor.getString(0);
				String date=cursor.getString(1);
				String type=cursor.getString(2);
				String body=cursor.getString(3);
				BaseApplication.smsDb.execSQL("insert into sms_info(name,phone,content,time) values(?, ?, ?, ?)",
						new Object[]{type,address,body,date});
				process++;
				callback.onBackUpSms(process);
				SystemClock.sleep(200);
			}
			cursor.close();
			return true;

		}

		return false;
	}

}
