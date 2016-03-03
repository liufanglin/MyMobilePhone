package com.shopex.phone.phone.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.shopex.phone.phone.bean.Contract;
import com.shopex.phone.phone.library.toolbox.LogUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsung on 2016/1/12.
 * 读取系统中的联系人
 */
public class ReadFriend {
    private static List<Contract> list=null;

    //查询联系人
    public static List  curesorContract(Context context){
        list=new ArrayList<>();
        //得到ContentResolver对象
        ContentResolver cr=context.getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor=cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        //向下移动光标
        while (cursor.moveToNext()){
            Contract con=new Contract();
            //取得联系人名字
            int nameFieldColumnIndex=cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String contact=cursor.getString(nameFieldColumnIndex);
            String contactId=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            con.setName(contact);
            //获取电话号码
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            while(phone.moveToNext()){
                String number=phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                con.setPhone(number);

            }
            list.add(con);
        }
        cursor.close();
        return list;
    }



}
