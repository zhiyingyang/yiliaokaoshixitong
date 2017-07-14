package com.jsmosce.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.jsmosce.adapter.ScoreRedultAdapter;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.base.BaseData;
import com.jsmosce.data.DataScoreResult;
import com.jsmosce.view.CustomizeDialog;
import com.jsmosce.view.TitleView;
import com.jsmosce.view.percentsuppor.PercentLinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//评分标准
public class ScoreResultsActivity extends AppCompatActivity implements View.OnClickListener {
    ScoreRedultAdapter scoreRedultAdapter;
    @Bind(R.id.score_title)
    TitleView scoreTitle;
    @Bind(R.id.top)
    PercentLinearLayout top;
    @Bind(R.id.score_res_rv)
    RecyclerView scoreResRv;
    @Bind(R.id.article_add_bu)
    Button articleAddBu;
    @Bind(R.id.article_sub_bu)
    Button articleSubBu;
    //    @Bind(R.id.fab)
//    FloatingActionMenu fab;
    EditText dengji;
    EditText qujian;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_results);
        ButterKnife.bind(this);
        initView();
        getData();
    }

    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        scoreResRv.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        scoreResRv.setLayoutManager(layoutManager);
        scoreRedultAdapter = new ScoreRedultAdapter(getApplicationContext());
        scoreResRv.setAdapter(scoreRedultAdapter);
        articleAddBu.setOnClickListener(this);
        articleSubBu.setOnClickListener(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecycleItemTouchHelper(scoreRedultAdapter));
        itemTouchHelper.attachToRecyclerView(scoreResRv);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_edit, null);

        dengji = (EditText) layout.findViewById(R.id.dengji);
        qujian = (EditText) layout.findViewById(R.id.qujian);

        dialog = new CustomizeDialog.Builder(this, R.style.Dialog).setTitle("请输入一个新的评分标准").setView(layout).setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataScoreResult.PointsInfoBean goodsInfoBean = new DataScoreResult.PointsInfoBean();
                goodsInfoBean.setDescribe(dengji.getText().toString());
                goodsInfoBean.setScore(qujian.getText().toString());
                qujian.setText("");
                dengji.setText("");
                // fab.close(true);
                scoreRedultAdapter.addDataOne(goodsInfoBean);
                scoreResRv.scrollToPosition(scoreRedultAdapter.getItemCount() - 1);
                dialog.dismiss();
                qujian.setText("");
                dengji.setText("");
            }
        }).myCreate();

//        dialog = new AlertDialog.Builder(this, R.style.DialogTwo)
//                .setTitle("请输入一个新的评分标准")
//                .setView(layout)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if ("".equals(dengji.getText())||"".equals(qujian.getText())){
//                            Tools.myMakeText(getApplicationContext(),"内容不能为空");
//                            return;
//                        }
//
//                        DataScoreResult.PointsInfoBean goodsInfoBean = new DataScoreResult.PointsInfoBean();
//                        goodsInfoBean.setDescribe(dengji.getText().toString());
//                        goodsInfoBean.setScore(qujian.getText().toString());
//                        qujian.setText("");
//                        dengji.setText("");
//                       // fab.close(true);
//                        scoreRedultAdapter.addDataOne(goodsInfoBean);
//                        scoreResRv.scrollToPosition(scoreRedultAdapter.getItemCount() - 1);
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
        }
    }


    public void submit() {
        if (scoreRedultAdapter.getDatas().size() == 0) {
            Tools.myMakeText(getApplicationContext(), "数据不能为空");
            return;
        }

        DataScoreResult dataScoreResult = new DataScoreResult();
        dataScoreResult.setInterface("points");
        dataScoreResult.setPack("Check");
        dataScoreResult.setCaseId(BaseApplication.caseId);
        dataScoreResult.setPointsInfo(scoreRedultAdapter.getDatas());

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Tools.toJson(dataScoreResult));
        Call<BaseData> call = repo.postBaseData(body);

        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call,
                                   Response<BaseData> response) {

                try {
                    if (response.body().getState() == 1) {
                        Tools.myMakeText(getApplicationContext(), "提交成功");
                        ScoreResultsActivity.this.finish();
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
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Check\",\"Interface\":\"standard\",\"caseId\":" + BaseApplication.caseId + "}");
        Call<DataScoreResult> call = repo.postScoreResultsList(body);

        call.enqueue(new Callback<DataScoreResult>() {
            @Override
            public void onResponse(Call<DataScoreResult> call,
                                   Response<DataScoreResult> response) {

                switch (response.body().getState()) {
                    case 1:
                        scoreRedultAdapter.upData(response.body().getPointsInfo());
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
            public void onFailure(Call<DataScoreResult> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }
}
