package com.shopex.phone.phone.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shopex.phone.phone.R;

/**
 * Created by samsung on 2016/1/9.
 * 电话拦截记录
 */
public class InterPhoneFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout= (RelativeLayout) inflater.inflate(R.layout.fragment_phone,null);
        return layout;
    }
}
