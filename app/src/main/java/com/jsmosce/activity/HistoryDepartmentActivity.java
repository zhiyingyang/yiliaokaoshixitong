package com.jsmosce.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.jsmosce.R;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.adapter.HistoryDepartmentAdapter;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.data.DataHospital;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//历史记录的科室界面
public class HistoryDepartmentActivity extends BaseActivity {

    @Bind(R.id.history_department_rv)
    RecyclerView historyDepartmentRv;
    HistoryDepartmentAdapter historyDepartmentAdapter;
    //private int departmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_department);
        ButterKnife.bind(this);
        initView();
        getData();
    }

    //初始化View
    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //设置布局管理器
        historyDepartmentRv.setHasFixedSize(true);

        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        historyDepartmentRv.setLayoutManager(layoutManager);

        historyDepartmentAdapter = new HistoryDepartmentAdapter(getApplicationContext());

        historyDepartmentRv.setAdapter(historyDepartmentAdapter);
    }

    public void getData() {
        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Other\",\"Interface\":\"department\",\"work_unitId\":"+ BaseApplication.user.getInfo().getWork_unitId()+"}");
        Call<DataHospital> call = repo.postHospital(body);

        call.enqueue(new Callback<DataHospital>() {
            @Override
            public void onResponse(Call<DataHospital> call,
                                   Response<DataHospital> response) {

                DataHospital dataHospital = response.body();
                switch (dataHospital.getState()) {
                    case 1:
                        historyDepartmentAdapter.upData(dataHospital.getDepartmentInfo());

                        break;
                    case 0:
                        Tools.myMakeText(getApplicationContext(), dataHospital.getMessage());
                        break;
                    case -1:
                        Tools.myMakeText(getApplicationContext(), "未知错误");
                        break;

                }
            }

            @Override
            public void onFailure(Call<DataHospital> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }

}
