package com.zy.diary.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zy.diary.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zhangyang on 2017/10/31.
 */
@Singleton
public class DbOpenHelper extends DaoMaster.OpenHelper {

    @Inject
    public DbOpenHelper(Context context) {
        super(context, AppConstants.DB_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
