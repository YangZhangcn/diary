package com.zy.diary.mvp.main;

import com.zy.diary.data.db.model.Diary;
import com.zy.diary.mvp.base.MvpView;

import java.util.List;

/**
 * Created by zhangyang on 2017/11/1.
 */

public interface MainView extends MvpView {

    void showDiaries(List<Diary> diaries);

    void showEmptyTip(CharSequence info);

    void hideEmptyTip();



}
