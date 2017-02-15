package com.udacity.porfolioapp.activity;

import android.app.Application;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.model.DaoMaster;
import com.udacity.porfolioapp.model.DaoSession;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Juan PC on 21/01/2017.
 */

public class BaseContextApplication extends Application {
    private DaoSession daoSession;
    public static final boolean ENCRYPTED = true;
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "movie-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
