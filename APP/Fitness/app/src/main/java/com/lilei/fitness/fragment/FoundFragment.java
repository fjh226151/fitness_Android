package com.lilei.fitness.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lilei.fitness.R;
import com.lilei.fitness.adapter.FoundNewsAdapter;
import com.lilei.fitness.entity.NewsListForFound;
import com.lilei.fitness.entity.NewsListItem;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.view.NewsDetailActivity;
import com.lilei.fitness.view.ReleaseNewsActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;

/**
 * Created by djzhao on 17/04/30.
 */

public class FoundFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ListView foundList;
    private FoundNewsAdapter adapter;
    private Context mContext;

    private List<NewsListForFound> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_found, null);
        findViewById(v);
        initView();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
       // reLoadNews();
    }


    public void findViewById(View v) {
        foundList = (ListView) v.findViewById(R.id.found_list);
    }

    public void initView() {
        mContext = getActivity();
        foundList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NewsDetailActivity.class);
        intent.putExtra("newsId", mList.get(position).getNewsId());
        startActivity(intent);
    }

}
