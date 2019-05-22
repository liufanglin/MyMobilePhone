package com.shopex.phone.phone.activity;

import com.shopex.phone.phone.bean.GooglePoiBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by liufanglin on 2019/5/22.
 */

public class GoogleAPIService {
    private static final String GOOGLE_SERVICE_API_KEY  = "AIzaSyBqb7tZicfEirhZsK-LJn2T8xb9hVKBgro";

    private Retrofit retrofit;

    public GoogleAPIService() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .build();

    }

    public void getPoi(double lat, double lng, int radius, String type, Callback<GooglePoiBean> callback) {

        GoogleMapApi googleMapApi = retrofit.create(GoogleMapApi.class);
        Call<GooglePoiBean> googlePoiBeanCall = googleMapApi.listRepos(lat + "," + lng, radius, type, "", GOOGLE_SERVICE_API_KEY);
        googlePoiBeanCall.enqueue(callback);

    }
}
