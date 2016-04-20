package com.shopex.phone.phone.library.db;

import android.content.Context;
import android.util.Log;

import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.db.DaoSession;
import com.shopex.phone.phone.db.User;
import com.shopex.phone.phone.db.UserDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by samsung on 2016/4/20.
 */
public class DbServiceUser {


    private static final String TAG = DbService.class.getSimpleName();
    private static DbServiceUser instance;
    private static Context appContext;
    private  DaoSession mDaoSession;
    private UserDao userDao;


    public static DbServiceUser getInstance(Context context) {
        if (instance == null) {
            instance = new DbServiceUser();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }

            instance.mDaoSession = BaseApplication.daoSessionUser;
            instance.userDao = instance.mDaoSession.getUserDao();
        }
        return instance;
    }
    //插入一个对象
    public  long insert(User note){
        return userDao.insert(note);
    }
    //根据用户名查询密码
    public User SelectNote(String name){
        QueryBuilder<User> qb=userDao.queryBuilder();
        qb.where(UserDao.Properties.Account.eq(name));
        if (qb.list().size()>0){
            return qb.list().get(0);
        }else {
            return null;
        }
    }
    //删除某个对象
    public void deleteNoteItem(User note){
        QueryBuilder<User> qb=userDao.queryBuilder();
        qb.where(UserDao.Properties.Account.eq(note.getAccount()));
        List<User> list =qb.build().list();
        userDao.deleteInTx(list);
    }
    public void update(User note){
        QueryBuilder<User> qb=userDao.queryBuilder();
        qb.where(UserDao.Properties.Id.eq(note.getId()));
        List<User> list =qb.build().list();
        userDao.update(note);
    }





    public User loadNote(long id) {
        return userDao.load(id);
    }

    public List<User> loadAllNote(){
        return userDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */
    public List<User> queryNote(String where, String... params){
        return userDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(User note){
        return userDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<User> list){
        if(list == null || list.isEmpty()){
            return;
        }
        userDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    User note = list.get(i);
                    userDao.insertOrReplace(note);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllNote(){
        userDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        userDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(User note){
        userDao.delete(note);
    }
}
