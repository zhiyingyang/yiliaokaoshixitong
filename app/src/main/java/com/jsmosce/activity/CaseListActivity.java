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
import com.jsmosce.adapter.CaseListAdapter;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.data.DataCaseList;
import com.jsmosce.view.TitleView;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//已有考案
public class CaseListActivity extends AppCompatActivity {

    @Bind(R.id.case_list_title)
    TitleView caseListTitle;
    @Bind(R.id.case_list_rv)
    RecyclerView caseListRv;
//    @Bind(R.id.fab)
//    FloatingActionMenu fab;
    CaseListAdapter caseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_list);
        ButterKnife.bind(this);
        initView();
        getData();
    }

    public void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        caseListRv.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        caseListRv.setLayoutManager(layoutManager);
        caseListAdapter = new CaseListAdapter(getApplicationContext());
        //设置Adapter
        caseListRv.setAdapter(caseListAdapter);
    }


    public void getData() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Check\",\"Interface\":\"before\",\"caseId\":" + BaseApplication.caseId + "}");
        Call<DataCaseList> call = repo.postCaseList(body);

        call.enqueue(new Callback<DataCaseList>() {
            @Override
            public void onResponse(Call<DataCaseList> call,
                                   Response<DataCaseList> response) {
                switch (response.body().getState()) {
                    case 1:
                        caseListAdapter.upData(response.body().getInfo());
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
            public void onFailure(Call<DataCaseList> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }

}
