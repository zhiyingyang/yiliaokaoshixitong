package com.jsmosce.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.jsmosce.R;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.adapter.HistoryCDetailsAdapter;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.data.DataHistoryCandidatesDetails;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//历史记录考生详情
public class HistoryCandidatesDetailsActivity extends AppCompatActivity {

    @Bind(R.id.history_candidates_details_rv)
    RecyclerView historyCandidatesDetailsRv;
    HistoryCDetailsAdapter scoreRedultAdapter;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_candidates_details);
        ButterKnife.bind(this);
        studentId = getIntent().getStringExtra("studentId");
        initView();
        getData();
    }
    //初始化view
    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        historyCandidatesDetailsRv.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        historyCandidatesDetailsRv.setLayoutManager(layoutManager);
        scoreRedultAdapter = new HistoryCDetailsAdapter(getApplicationContext());
        historyCandidatesDetailsRv.setAdapter(scoreRedultAdapter);
    }

    //获取数据
    public void getData() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Other\",\"Interface\":\"result\",\"caseId\":" + BaseApplication.caseId + ",\"studentId\":" + studentId + "}");
        Call<DataHistoryCandidatesDetails> call = repo.postHistoryCandidatesDetails(body);

        call.enqueue(new Callback<DataHistoryCandidatesDetails>() {
            @Override
            public void onResponse(Call<DataHistoryCandidatesDetails> call,
                                   Response<DataHistoryCandidatesDetails> response) {

                switch (response.body().getState()) {
                    case 1:
                        scoreRedultAdapter.upData(response.body().getResultInfo());
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
            public void onFailure(Call<DataHistoryCandidatesDetails> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }

}
