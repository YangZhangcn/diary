package com.zy.diary.data.db;

import com.zy.diary.data.db.model.Diary;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zhangyang on 2017/11/1.
 */
@Singleton
public class DbHelperImpl implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public DbHelperImpl(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public void saveDiary(Diary diary) {
        mDaoSession.getDiaryDao().insert(diary);
    }

    @Override
    public List<Diary> getDiaryList() {
        return mDaoSession.getDiaryDao().loadAll();
    }
}
