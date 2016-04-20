package com.shopex.phone.phone.library.db;

import android.content.Context;
import android.util.Log;

import com.shopex.phone.phone.common.BaseApplication;
import com.shopex.phone.phone.db.ContactUser;
import com.shopex.phone.phone.db.ContactUserDao;
import com.shopex.phone.phone.db.DaoSession;
import com.shopex.phone.phone.db.User;
import com.shopex.phone.phone.db.UserDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by samsung on 2016/1/13.
 */
public class DbService {
    private static final String TAG = DbService.class.getSimpleName();
    private static DbService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private ContactUserDao contactUserDao;

    private UserDao userDao;


    public static DbService getInstance(Context context) {
        if (instance == null) {
            instance = new DbService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.daoSession;
            instance.contactUserDao = instance.mDaoSession.getContactUserDao();
        }
        return instance;
    }
    //插入一个对象
    public  long insert(ContactUser note){
        return contactUserDao.insert(note);
    }
    //根据用户名查询密码
    public ContactUser SelectNote(String name){
        QueryBuilder<ContactUser> qb=contactUserDao.queryBuilder();
        qb.where(ContactUserDao.Properties.Name.eq(name));
        if (qb.list().size()>0){
            return qb.list().get(0);
        }else {
            return null;
        }
    }
    //删除某个对象
    public void deleteNoteItem(ContactUser note){
        QueryBuilder<ContactUser> qb=contactUserDao.queryBuilder();
        qb.where(ContactUserDao.Properties.Name.eq(note.getName()));
        List<ContactUser> list =qb.build().list();
        contactUserDao.deleteInTx(list);
    }
    public void update(ContactUser note){
        QueryBuilder<ContactUser> qb=contactUserDao.queryBuilder();
        qb.where(ContactUserDao.Properties.Id.eq(note.getId()));
        List<ContactUser> list =qb.build().list();
        contactUserDao.update(note);
    }





    public ContactUser loadNote(long id) {
        return contactUserDao.load(id);
    }

    public List<ContactUser> loadAllNote(){
        return contactUserDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */
    public List<ContactUser> queryNote(String where, String... params){
        return contactUserDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(ContactUser note){
        return contactUserDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<ContactUser> list){
        if(list == null || list.isEmpty()){
            return;
        }
        contactUserDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    ContactUser note = list.get(i);
                    contactUserDao.insertOrReplace(note);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllNote(){
        contactUserDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        contactUserDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(ContactUser note){
        contactUserDao.delete(note);
    }
}
