package com.shopex.phone.phone.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.shopex.phone.phone.db.ContactUser;
import com.shopex.phone.phone.db.User;

import com.shopex.phone.phone.db.ContactUserDao;
import com.shopex.phone.phone.db.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig contactUserDaoConfig;
    private final DaoConfig userDaoConfig;

    private final ContactUserDao contactUserDao;
    private final UserDao userDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        contactUserDaoConfig = daoConfigMap.get(ContactUserDao.class).clone();
        contactUserDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        contactUserDao = new ContactUserDao(contactUserDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(ContactUser.class, contactUserDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        contactUserDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
    }

    public ContactUserDao getContactUserDao() {
        return contactUserDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
