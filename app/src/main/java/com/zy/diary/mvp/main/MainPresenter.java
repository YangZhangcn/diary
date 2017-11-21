package com.zy.diary.mvp.main;

import com.zy.diary.data.db.model.Diary;
import com.zy.diary.mvp.base.MvpPresenter;

import java.util.List;

/**
 * Created by zhangyang on 2017/11/1.
 */

public interface MainPresenter<V extends MainView> extends MvpPresenter<V> {

    List<Diary> getDiaries();

    void gotoEditPage(Diary diary);

    List<Diary> searchDairy(String info,int type);

    void getWeather(String City);
}
