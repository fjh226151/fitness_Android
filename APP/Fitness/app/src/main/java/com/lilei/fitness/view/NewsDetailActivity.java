package com.lilei.fitness.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lilei.fitness.R;
import com.lilei.fitness.adapter.FoundNewsAdapter;
import com.lilei.fitness.adapter.NewsDetailCommnetsAdapter;
import com.lilei.fitness.entity.Comment;
import com.lilei.fitness.entity.NewsDetail;
import com.lilei.fitness.entity.NewsListForFound;
import com.lilei.fitness.fragment.FoundFragment;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.DateUtils;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.view.base.BaseActivity;
import com.lilei.fitness.widgets.ListViewWithScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by djzhao on 17/05/02.
 */

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {

    private NewsDetailCommnetsAdapter adapter;
    private NewsDetail newsDetail;
    private List<Comment> mList;

    private String TITLE_NAME = "新鲜事详情";
    private View title_back;
    private TextView titleText;

    private TextView usernameTV;
    private TextView titleTV;
    private TextView releaseTimeTV;
    private ImageView imageIV;
    private TextView contentTV;

    private LinearLayout commentPane;
    private EditText addCommentET;
    private ImageView addCommentIV;
    private boolean isShowCommentPane;


    private ListViewWithScrollView commentsLV;
    private LinearLayout commentLL;
    private LinearLayout favorLL;

    private Context mContext;

    private int newsId;
    private String replyUsername;

    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        newsId = getIntent().getIntExtra("newsId", 0);
        setContentView(R.layout.activity_news_detail);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);

        usernameTV = $(R.id.news_detail_username);
        releaseTimeTV = $(R.id.news_detail_time);
        imageIV = $(R.id.news_detail_image);
        contentTV = $(R.id.news_detail_content);
        titleTV = $(R.id.detail_title);

        commentLL = $(R.id.news_detail_add_comment);
        favorLL = $(R.id.news_detail_add_favor);

        commentPane = $(R.id.news_detail_add_commment_pane);
        addCommentET = $(R.id.news_detail_add_commment_text);
        addCommentIV = $(R.id.news_detail_add_commment_btn);

        commentsLV = $(R.id.news_detail_comment);
    }

    @Override
    protected void initView() {
        mContext = this;
        this.titleText.setText(TITLE_NAME);

        this.title_back.setOnClickListener(this);
        commentLL.setOnClickListener(this);
        favorLL.setOnClickListener(this);
        addCommentIV.setOnClickListener(this);

        uiFlusHandler = new MyDialogHandler(mContext, "加载中...");
       // refreshData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.news_detail_add_comment:
                showCommemtPane();
                break;
            case R.id.news_detail_add_favor:
               // addNewFavor();
                break;
            case R.id.news_detail_add_commment_btn:
              //  addNewComment();
                break;
        }
    }

    private void showCommemtPane() {
        isShowCommentPane = !isShowCommentPane;
        if (isShowCommentPane) {
            commentPane.setVisibility(View.VISIBLE);
            addCommentET.setHint("发表新评论");
            replyUsername = "";
            addCommentET.requestFocus();
        } else {
            commentPane.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(addCommentET.getWindowToken(), 0);
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
