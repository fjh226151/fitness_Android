package com.lilei.fitness.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lilei.fitness.R;
import com.lilei.fitness.adapter.GoodsListAdapter;
import com.lilei.fitness.bean.eatFoods;
import com.lilei.fitness.bean.goods;
import com.lilei.fitness.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GoodsListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private GoodsListAdapter adapter;
    private List<String> list = new ArrayList<>();


    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        recyclerView = findViewById(R.id.rv);
        adapter = new GoodsListAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        BmobQuery<goods> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<goods>() {
            @Override
            public void done(List<goods> list, BmobException e) {
                if (e == null) {
                    showToast("获取到数据:" + list);
                    for (goods foods : list) {
                        GoodsListActivity.this.list.add("食物名称:" + foods.getGoodName() + "==>消耗热量:" + String.valueOf(foods.getDepleteGoodsValue().toString()));
                    }
                } else {
                    showToast("暂无数据,请联系管理员");
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
