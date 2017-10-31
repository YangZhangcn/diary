package com.zy.diary.mvp.base;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

/**
 * Created by zhangyang on 2017/10/31.
 */
public abstract class BaseFragment extends Fragment implements MvpView{

    private BaseActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity){
            mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void showLoading() {
        if (mActivity != null)
            mActivity.showLoading();
    }

    @Override
    public void hideLoading() {
        if (mActivity != null)
            mActivity.hideLoading();
    }

    @Override
    public void onError(@StringRes int resId) {
        if (mActivity != null)
            mActivity.onError(resId);
    }

    @Override
    public void onError(String message) {
        if (mActivity != null)
            mActivity.onError(message);
    }

    @Override
    public boolean isNetworkConnected() {
        if (mActivity != null)
            return mActivity.isNetworkConnected();
        return false;
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }
}
