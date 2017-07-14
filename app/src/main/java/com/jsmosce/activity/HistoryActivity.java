package com.jsmosce.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.jsmosce.R;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.adapter.HistoryAdapter;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.data.DataHomeTest;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//历史记录界面
public class HistoryActivity extends BaseActivity {

    @Bind(R.id.history_rv)
    RecyclerView historyRv;
    HistoryAdapter HistoryAdapter;
    private int DepartmentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        initView();
        getData();
    }

    //初始化方法
    public void initView() {
        DepartmentId = getIntent().getIntExtra("DepartmentId", -1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //设置布局管理器
        historyRv.setHasFixedSize(true);

        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        historyRv.setLayoutManager(layoutManager);

        HistoryAdapter = new HistoryAdapter(getApplicationContext());

        historyRv.setAdapter(HistoryAdapter);

    }

    //请求数据
    public void getData() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body;
        if (DepartmentId == -1) {
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Other\",\"Interface\":\"index\",\"UserId\":" + BaseApplication.user.getInfo().getId() + "}");
        } else {
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Other\",\"Interface\":\"index\",\"UserId\":"+BaseApplication.user.getInfo().getId()+",\"departmentId\":"+DepartmentId+"}");
        }
        Call<DataHomeTest> call = repo.postHomeTestRoute(body);

        call.enqueue(new Callback<DataHomeTest>() {
            @Override
            public void onResponse(Call<DataHomeTest> call,
                                   Response<DataHomeTest> response) {

                DataHomeTest dataScoreing = response.body();

                switch (dataScoreing.getState()) {
                    case 1:
                        //添加数据
                        HistoryAdapter.upData(dataScoreing.getInfo());
                        break;
                    case 0:
                        Tools.myMakeText(getApplicationContext(), dataScoreing.getMessage());
                        break;
                    case -1:
                        Tools.myMakeText(getApplicationContext(), "未知错误");
                        break;
                }
            }

            @Override
            public void onFailure(Call<DataHomeTest> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }


}
