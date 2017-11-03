package com.zy.diary.mvp.main;

import com.zy.diary.mvp.base.MvpPresenter;

/**
 * Created by zhangyang on 2017/11/1.
 */

public interface MainPresenter<V extends MainView> extends MvpPresenter<V> {

    void getLocation(String City);
}
