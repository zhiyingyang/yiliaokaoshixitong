package com.jsmosce.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jsmosce.R;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.adapter.RecycleItemTouchHelper;
import com.jsmosce.adapter.ScoreItemAdapter;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.base.BaseData;
import com.jsmosce.data.DataSubmitScoreKind;
import com.jsmosce.view.CustomizeDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//评分类目
public class RatingItemActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.rating_rv)
    RecyclerView ratingRv;
    @Bind(R.id.article_add_bu)
    Button articleAddBu;
    @Bind(R.id.article_sub_bu)
    Button articleSubBu;
    ScoreItemAdapter scoreItemAdapter;
    EditText articleName;
    //    @Bind(R.id.fab)
//    FloatingActionMenu fab;
    CustomizeDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_item);
        ButterKnife.bind(this);
        initView();
    }


    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        ratingRv.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        ratingRv.setLayoutManager(layoutManager);
        scoreItemAdapter = new ScoreItemAdapter(getApplicationContext());
        //设置Adapter
        ratingRv.setAdapter(scoreItemAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecycleItemTouchHelper(scoreItemAdapter));
        itemTouchHelper.attachToRecyclerView(ratingRv);

        articleAddBu.setOnClickListener(this);
        articleSubBu.setOnClickListener(this);
        getData();

        LayoutInflater inflater = LayoutInflater.from(this);

        View layout = inflater.inflate(R.layout.dialog_edittext, null);
        articleName = (EditText) layout.findViewById(R.id.edit);

        dialog = new CustomizeDialog.Builder(this, R.style.Dialog).setTitle("请输入一个新的类目").setView(layout).setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(articleName.getText().toString())) {
                    Tools.myMakeText(getApplicationContext(), "类目标题不能为空");
                    return;
                }
                DataSubmitScoreKind.CateInfoBean goodsInfoBean = new DataSubmitScoreKind.CateInfoBean();
                goodsInfoBean.setDescribe(articleName.getText().toString());
                scoreItemAdapter.addDataOne(goodsInfoBean);
                //  fab.close(true);
                ratingRv.scrollToPosition(scoreItemAdapter.getItemCount() - 1);
                dialog.dismiss();
                articleName.setText("");
            }
        }).myCreate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.article_add_bu:

                dialog.show();

//                new AlertDialog.Builder(this, R.style.DialogTwo)
//                        .setTitle("请输入一个新的类目")
//                        .setView(articleName)
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if ("".equals(articleName.getText().toString())) {
//                                    Tools.myMakeText(getApplicationContext(), "类目标题不能为空");
//                                    return;
//                                }
//                                DataSubmitScoreKind.CateInfoBean goodsInfoBean = new DataSubmitScoreKind.CateInfoBean();
//                                goodsInfoBean.setDescribe(articleName.getText().toString());
//                                scoreItemAdapter.addDataOne(goodsInfoBean);
//                                //  fab.close(true);
//                                ratingRv.scrollToPosition(scoreItemAdapter.getItemCount() - 1);
//
//                            }
//                        })
//                        .setNegativeButton("取消", null)
//                        .show();


                break;

            case R.id.article_sub_bu:
                submit();
                break;
        }
    }


    public void submit() {
        if (scoreItemAdapter.getDatas().size() == 0) {
            Tools.myMakeText(getApplicationContext(), "数据不能为空");
            return;
        }

        DataSubmitScoreKind dataSubmitArticle = new DataSubmitScoreKind();
        dataSubmitArticle.setInterface("category");
        dataSubmitArticle.setPack("Check");
        dataSubmitArticle.setCaseId(BaseApplication.caseId);
        dataSubmitArticle.setCateInfo(scoreItemAdapter.getDatas());

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
                       // BaseApplication.flag[2] = 1;
                        RatingItemActivity.this.finish();
                    } else {
                        Tools.myMakeText(getApplicationContext(), response.body().getMessage());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<BaseData> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });
    }


    public void getData() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Check\",\"Interface\":\"cate\",\"caseId\":" + BaseApplication.caseId + "}");
        Call<DataSubmitScoreKind> call = repo.postScoreKindList(body);

        call.enqueue(new Callback<DataSubmitScoreKind>() {
            @Override
            public void onResponse(Call<DataSubmitScoreKind> call,
                                   Response<DataSubmitScoreKind> response) {
                switch (response.body().getState()) {
                    case 1:
                        scoreItemAdapter.upData(response.body().getCateInfo());
                        break;
                    case 0:
                        Tools.myMakeText(getApplicationContext(), response.body().getMessage());
                        break;
                    case -1:
                        Tools.myMakeText(getApplicationContext(), "未知错误");
                        break;
                }

            }

            @Override
            public void onFailure(Call<DataSubmitScoreKind> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }

}
