package com.shopex.phone.phone.activity;

import com.shopex.phone.phone.bean.GooglePoiBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liufanglin on 2019/5/22.
 */

public interface GoogleMapApi {

    @GET("/maps/api/place/nearbysearch/json")
    Call<GooglePoiBean> listRepos(@Query("location") String location,
                                  @Query("radius") int radius,
                                  @Query("types") String types,
                                  @Query("name") String name,
                                  @Query("key") String key);
}
