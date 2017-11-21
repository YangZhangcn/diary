package com.zy.diary.mvp.main;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.zy.diary.data.db.model.Diary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyang on 2017/11/21.
 */

public class DiaryListAdapter extends RecyclerView.Adapter {

    private List<Diary> diaries = new ArrayList<>();

    private MainPresenter presenter;

    public DiaryListAdapter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    public List<Diary> getDiaries() {
        return diaries;
    }

    public void setDiaries(List<Diary> diaries) {
        this.diaries.clear();
        addDiarys(diaries);
    }

    public void addDiarys(List<Diary> diaries) {
        if (diaries != null)
            this.diaries.addAll(diaries);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiaryItemHolder(parent, presenter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DiaryItemHolder diaryItemHolder = (DiaryItemHolder) holder;
        diaryItemHolder.setDiary(diaries.get(position));
    }

    @Override
    public int getItemCount() {
        return diaries.size();
    }
}
