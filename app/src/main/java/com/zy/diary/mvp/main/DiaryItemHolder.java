package com.zy.diary.mvp.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zy.diary.R;
import com.zy.diary.data.db.model.Diary;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyang on 2017/11/21.
 */

public class DiaryItemHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_diary_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_tags)
    TextView tvTags;
    @BindView(R.id.tv_diary_date)
    TextView tvDate;
    @BindView(R.id.iv_weather)
    ImageView ivWeather;

    private Diary diary;

    MainPresenter mainPresenter;

    public DiaryItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        initView(itemView);
    }

    public DiaryItemHolder(ViewGroup parent,MainPresenter presenter) {
        this(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary_list,parent,false));
        mainPresenter = presenter;
    }

    private void initView(View itemView){
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setDiary(Diary diary){
        this.diary = diary;
        upDateViews();
    }

    private void upDateViews(){
        tvContent.setText(diary.getContent());
        tvTitle.setText(diary.getTitle());
        tvDate.setText(diary.getDate());
        tvTags.setText(diary.getTags());
    }


}
