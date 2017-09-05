package com.example.twapp.control;

import android.annotation.SuppressLint;
import android.app.Application;

import com.example.twapp.db.DaoMaster;
import com.example.twapp.db.DaoSession;
import com.example.twapp.db.DbHelper;

import org.greenrobot.greendao.database.Database;

/**
 * Created by lenovo-pc on 2017/8/29.
 */

public class TwApplication extends Application {
    private static TwApplication sInstance;
    private DaoSession daoSession;
    //    private ComponentName jobService;

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        DbHelper helper = new DbHelper(this, "twdb", null);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
//        jobService = new ComponentName(this, UploadingSer.class);
    }

    public static TwApplication getsInstance() {
        return sInstance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
