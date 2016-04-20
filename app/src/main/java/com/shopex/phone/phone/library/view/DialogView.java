package com.shopex.phone.phone.library.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shopex.phone.phone.R;

/**
 * Created by samsung on 2016/4/20.
 */
public class DialogView {
    public static android.app.Dialog showAlertDialog(Context c ,String message ,String cancelText,String confirmText,
                                                     View.OnClickListener cancelListener, View.OnClickListener okListener , boolean isShowGender , View.OnClickListener genderListener){
        final android.app.Dialog dialog = new android.app.Dialog(c, R.style.Theme_dialog);
        View view = LayoutInflater.from(c).inflate(R.layout.cunstom_dialog_view, null);
        ((TextView) view.findViewById(R.id.dialog_message)).setText(message);
        TextView cancel = (TextView) view.findViewById(R.id.dialog_cancel_btn);
        if (!TextUtils.isEmpty(cancelText)) {
            cancel.setVisibility(View.VISIBLE);
            view.findViewById(R.id.dialog_line).setVisibility(View.VISIBLE);
            cancel.setText(cancelText);
        }
        if (cancelListener != null) {//自定义点击事件
            cancel.setOnClickListener(cancelListener);
        } else {
            cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        TextView okBtn = (TextView) view.findViewById(R.id.dialog_conform_btn);
        if (isShowGender) {
            okBtn.setVisibility(View.GONE);
            if (genderListener != null) {
                view.findViewById(R.id.dialog_cancel_gender).setVisibility(View.VISIBLE);
                view.findViewById(R.id.dialog_gender1).setOnClickListener(genderListener);
                view.findViewById(R.id.dialog_gender2).setOnClickListener(genderListener);
                view.findViewById(R.id.dialog_gender3).setOnClickListener(genderListener);
            }
        } else {
            if (!TextUtils.isEmpty(confirmText)) {
                okBtn.setText(confirmText);
            }
            if (okListener != null) {//自定义点击事件
                okBtn.setOnClickListener(okListener);
            } else {
                okBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        }
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }
}
