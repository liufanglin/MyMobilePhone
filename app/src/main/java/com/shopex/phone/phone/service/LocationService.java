package com.shopex.phone.phone.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.pdf.PdfRenderer;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;

import com.shopex.phone.phone.library.toolbox.PreferencesUtils;

/**
 * Created by samsung on 2016/3/1.
 * 获取手机的位置服务
 */
public class LocationService extends Service implements LocationListener{

    private LocationManager locationManager;
    private Location location;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
     //   locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onLocationChanged(Location location) {
        StringBuffer sb=new StringBuffer();
        sb.append("精确度:"+location.getAccuracy()+"\n");
        sb.append("移动速度:"+location.getSpeed()+"\n");
        sb.append("纬度:"+location.getLatitude()+"\n");
        sb.append("经度:"+location.getLongitude()+"\n");
        String result = sb.toString();
        SmsManager.getDefault().sendTextMessage(PreferencesUtils.getString(this,"bindphone"), null, result, null, null);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
