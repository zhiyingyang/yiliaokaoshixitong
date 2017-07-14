package com.jsmosce.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jsmosce.R;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.adapter.ArticleAdapter;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.base.BaseData;
import com.jsmosce.data.DataSubmitArticle;
import com.jsmosce.view.CustomizeDialog;
import com.jsmosce.view.TitleView;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ArticleActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.article_rv)
    RecyclerView articleRv;

    ArticleAdapter articleAdapter;
    @Bind(R.id.article_title)
    TitleView articleTitle;
    //    @Bind(R.id.fab)
//    FloatingActionMenu fab;
    @Bind(R.id.article_add_bu)
    Button articleAddBu;
    @Bind(R.id.article_sub_bu)
    Button articleSubBu;
    @Bind(R.id.article_sub_skip)
    Button articleSubSkip;
    //考案id
    //private String testId;
    //物品类型
    private int type;

    EditText wuping;
    EditText number;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_article);
        // AndroidBug5497Workaround.assistActivity(this);
        ButterKnife.bind(this);
        //  testId = getIntent().getStringExtra("rId");
        type = getIntent().getIntExtra("type", 1);
        initView();
        getData();

    }

    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        articleRv.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        articleRv.setLayoutManager(layoutManager);
        articleAdapter = new ArticleAdapter(getApplicationContext());
        //设置Adapter
        articleRv.setAdapter(articleAdapter);

        articleAddBu.setOnClickListener(this);
        articleSubBu.setOnClickListener(this);
        articleSubSkip.setOnClickListener(this);

        articleTitle.setTitle(type == 1 ? "添加考场物品" : "添加学生物品");

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_edit, null);

        wuping = (EditText) layout.findViewById(R.id.dengji);
        number = (EditText) layout.findViewById(R.id.qujian);
        number.setHint("数量");
        wuping.setHint("物品");

        dialog = new CustomizeDialog.Builder(this, R.style.Dialog).setTitle("请输入一个新的物品").setView(layout).setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSubmitArticle.GoodsInfoBean goodsInfoBean = new DataSubmitArticle.GoodsInfoBean();
                goodsInfoBean.setNumber(number.getText().toString());
                goodsInfoBean.setName(wuping.getText().toString());
                goodsInfoBean.setType(type);
                articleAdapter.addDataOne(goodsInfoBean);

                number.setText("");
                wuping.setText("");
                dialog.dismiss();
            }
        }).myCreate();


//        dialog = new AlertDialog.Builder(this, R.style.DialogTwo)
//                .setTitle("请输入一个新的物品")
//                .setView(layout)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        if ("".equals(wuping.getText()) || "".equals(number.getText())) {
//                            Tools.myMakeText(getApplicationContext(), "内容不能为空");
//                            return;
//                        }
//
//                        DataSubmitArticle.GoodsInfoBean goodsInfoBean = new DataSubmitArticle.GoodsInfoBean();
//                        goodsInfoBean.setNumber(number.getText().toString());
//                        goodsInfoBean.setName(wuping.getText().toString());
//                        goodsInfoBean.setType(type);
//                        articleAdapter.addDataOne(goodsInfoBean);
//
//                        number.setText("");
//                        wuping.setText("");
//                       // fab.close(true);
//                    }
//                }).setNegativeButton("取消", null).create();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.article_add_bu:
                dialog.show();
                break;

            case R.id.article_sub_bu:
                submit();
                break;
            case R.id.article_sub_skip:
                if (type == 1) {
                    getIntent().putExtra("type", 2);
                    ArticleActivity.this.startActivity(getIntent());
                } else {
                    ArticleActivity.this.finish();
                }
                break;

        }
    }


    public void submit() {

        DataSubmitArticle dataSubmitArticle = new DataSubmitArticle();
        dataSubmitArticle.setInterface("goodsAdd");
        dataSubmitArticle.setPack("Case");
        dataSubmitArticle.setGoodsInfo(articleAdapter.getListData());
        dataSubmitArticle.setType(type);
        dataSubmitArticle.setCaseId(BaseApplication.caseId);

        for (DataSubmitArticle.GoodsInfoBean d : articleAdapter.getListData()) {
            d.setType(type);
        }

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Tools.toJson(dataSubmitArticle));
        Call<BaseData> call = repo.postBaseData(body);

        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call,
                                   Response<BaseData> response) {

                try {
                    if (response.body().getState() == 1) {
                        Tools.myMakeText(getApplicationContext(), "提交成功");
                        // BaseApplication.flag[0] = 1;
                        if (type == 1) {
                            getIntent().putExtra("type", 2);
                            ArticleActivity.this.startActivity(getIntent());
                            ArticleActivity.this.finish();
                        } else {
                            ArticleActivity.this.finish();
                        }
                    } else {
                        Tools.myMakeText(getApplicationContext(), response.body().getMessage());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<BaseData> call, Throwable t) {
                Tools.myMakeText(ArticleActivity.this.getApplicationContext(), t.getMessage());
            }
        });
    }

    public void getData() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Case\",\"Interface\":\"goods\",\"caseId\":" + BaseApplication.caseId + ",\"type\":" + type + "}");
        Call<DataSubmitArticle> call = repo.postArticle(body);

        call.enqueue(new Callback<DataSubmitArticle>() {
            @Override
            public void onResponse(Call<DataSubmitArticle> call,
                                   Response<DataSubmitArticle> response) {

                DataSubmitArticle dataCaseDesign = response.body();

                switch (dataCaseDesign.getState()) {
                    case 1:
                        articleAdapter.upData(dataCaseDesign.getGoodsInfo());
                        break;
                    case 0:
                        Tools.myMakeText(getApplicationContext(), dataCaseDesign.getMessage());
                        break;
                    case -1:
                        Tools.myMakeText(getApplicationContext(), "未知错误");
                        break;

                }
            }

            @Override
            public void onFailure(Call<DataSubmitArticle> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }

}
