package com.shopex.phone.phone.library.db;

import android.content.Context;

import com.shopex.phone.phone.db.DaoMaster;
import com.shopex.phone.phone.db.DaoSession;
import com.shopex.phone.phone.library.constants.AppConstants;

/**
 * Created by samsung on 2016/1/13.
 */
public class DaoHelp {
    public static DaoMaster daoMaster;
    public static DaoSession daoSession;
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, AppConstants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}
