package com.zy.diary.mvp.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zy.diary.R;
import com.zy.diary.mvp.base.BaseActivity;

/**
 * Created by zhangyang on 2017/11/21.
 */

public class EditDiaryActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary);
    }
}
