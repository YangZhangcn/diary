package com.zy.diary.mvp.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.zy.diary.R;
import com.zy.diary.mvp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyang on 2017/11/21.
 */

public class EditDiaryActivity extends BaseActivity implements View.OnFocusChangeListener {

    @BindView(R.id.textInputLayout)
    TextInputLayout inputLayoutTitle;
    @BindView(R.id.textInputLayout_content)
    TextInputLayout inputLayoutContent;

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        etTitle.setOnFocusChangeListener(this);
        etContent.setOnFocusChangeListener(this);
        inputLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etContent.requestFocus();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.et_title:
                if (b){
                    ViewCompat.animate(inputLayoutTitle).setDuration(250).translationZ(30).start();
                    ViewCompat.animate(inputLayoutContent).setDuration(250).translationZ(0).start();
                }
                break;
            case R.id.et_content:
                if (b){
                    ViewCompat.animate(inputLayoutContent).setDuration(250).translationZ(30).start();
                    ViewCompat.animate(inputLayoutTitle).setDuration(250).translationZ(0).start();
                }
                break;
        }
    }
}
