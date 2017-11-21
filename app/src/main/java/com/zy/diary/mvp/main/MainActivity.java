package com.zy.diary.mvp.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zy.diary.R;
import com.zy.diary.data.db.model.Diary;
import com.zy.diary.mvp.base.BaseActivity;
import com.zy.diary.widget.GapItemDecoration;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    @BindView(R.id.tv_empty_tip)
    TextView tvEmptyTip;

    @Inject
    MainPresenter<MainView> mPresenter;

    DiaryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUnbinder(ButterKnife.bind(this));
        getActivityComponent().inject(this);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.addItemDecoration(new GapItemDecoration(this));
        adapter = new DiaryListAdapter(mPresenter);
        rvMain.setAdapter(adapter);
        hideEmptyTip();
        mPresenter.onAttach(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.gotoEditPage(null);
            }
        });

    }

    @Override
    public int getSnackLayoutId() {
        return R.id.cl_main;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.onPermissionResult(requestCode,permissions,grantResults);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDiaries(List<Diary> diaries) {
        adapter.setDiaries(diaries);
    }

    @Override
    public void showEmptyTip(CharSequence info) {
        rvMain.setVisibility(View.GONE);
        tvEmptyTip.setVisibility(View.VISIBLE);
        tvEmptyTip.setText(info);
    }

    @Override
    public void hideEmptyTip() {
        rvMain.setVisibility(View.VISIBLE);
        tvEmptyTip.setVisibility(View.GONE);
    }
}
