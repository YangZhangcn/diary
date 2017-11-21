package com.zy.diary.data.db;

import com.zy.diary.data.db.model.Diary;

import java.util.List;

/**
 * Created by zhangyang on 2017/10/31.
 * 数据库的操作接口
 */
public interface DbHelper {
    void saveDiary(Diary diary);
    List<Diary> getDiaryList();
}
