package com.jsmosce.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import com.jsmosce.R;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.adapter.CandidatesManagementAdapter;
import com.jsmosce.adapter.RecycleItemTouchHelper;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.base.BaseData;
import com.jsmosce.data.DataAllCandiates;
import com.jsmosce.data.DataSubmitCandidates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//考生管理
public class CandidatesManagementActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.candudates_m_rv)
    RecyclerView candudatesMRv;
    @Bind(R.id.score_add)
    Button scoreAdd;
    @Bind(R.id.score_updata)
    Button scoreUpdata;
    CandidatesManagementAdapter candidatesManagementAdapter;
    DataAllCandiates DataAllCandiates;
    private static final int Mars = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates_management);
        ButterKnife.bind(this);
        initView();
        getData();
    }

    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        candudatesMRv.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        candudatesMRv.setLayoutManager(layoutManager);
        candidatesManagementAdapter = new CandidatesManagementAdapter(getApplicationContext());

        //设置Adapter
        candudatesMRv.setAdapter(candidatesManagementAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecycleItemTouchHelper(candidatesManagementAdapter));
        itemTouchHelper.attachToRecyclerView(candudatesMRv);

        scoreAdd.setOnClickListener(this);
        scoreUpdata.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.score_add:
                if (DataAllCandiates == null || DataAllCandiates.getInfo() == null || DataAllCandiates.getInfo().getStudentInfo() == null)
                    return;
                Intent intent = new Intent();
                intent.setClass(CandidatesManagementActivity.this, CandidatesAllListActivity.class);

                intent.putExtra("StudentInfo", (Serializable) DataAllCandiates.getInfo().getStudentInfo());

                startActivityForResult(intent, Mars);
                break;
            case R.id.score_updata:
                submitData();
                break;
        }

    }

    public void getData() {
        if (BaseApplication.user == null) return;
        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Case\",\"Interface\":\"examinee\",\"caseId\":" + BaseApplication.caseId + "}");
        Call<DataAllCandiates> call = repo.postAllCandidates(body);

        call.enqueue(new Callback<DataAllCandiates>() {
            @Override
            public void onResponse(Call<DataAllCandiates> call,
                                   Response<DataAllCandiates> response) {

                DataAllCandiates = response.body();

                switch (DataAllCandiates.getState()) {
                    case 1:
                        candidatesManagementAdapter.addData(DataAllCandiates.getInfo().getCheckedInfo());
                        break;
                    case 0:
                        Tools.myMakeText(getApplicationContext(), DataAllCandiates.getMessage());
                        break;
                    case -1:
                        Tools.myMakeText(getApplicationContext(), "未知错误");
                        break;

                }
            }

            @Override
            public void onFailure(Call<DataAllCandiates> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Mars && resultCode == Activity.RESULT_OK && data != null) {
            candidatesManagementAdapter.addData((List<DataAllCandiates.InfoBean.StudentInfoBean>) data.getSerializableExtra("datas"));
        }
    }


    public void submitData() {
        DataSubmitCandidates dataSubmitCandidates = new DataSubmitCandidates();
        dataSubmitCandidates.setInterface("examSubmit");
        dataSubmitCandidates.setPack("Case");
        dataSubmitCandidates.setCaseId(BaseApplication.caseId);
        List<DataSubmitCandidates.ExamInfoBean> examInfoBeens = new ArrayList<DataSubmitCandidates.ExamInfoBean>();

        for (int i = 0; i < candidatesManagementAdapter.getDatas().size(); i++) {
            DataSubmitCandidates.ExamInfoBean examInfoBean = new DataSubmitCandidates.ExamInfoBean();
            examInfoBean.setId(candidatesManagementAdapter.getDatas().get(i).getId());
            examInfoBeens.add(examInfoBean);
        }
        dataSubmitCandidates.setExamInfo(examInfoBeens);

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"
        ), Tools.toJson(dataSubmitCandidates));
        Call<BaseData> call = repo.postBaseData(body);

        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call,
                                   Response<BaseData> response) {

                BaseData baseData = response.body();

                switch (baseData.getState()) {
                    case 1:
                        Tools.myMakeText(getApplicationContext(), baseData.getMessage());
                        CandidatesManagementActivity.this.finish();
                        break;
                    case 0:
                        Tools.myMakeText(getApplicationContext(), baseData.getMessage());
                        break;
                    case -1:
                        Tools.myMakeText(getApplicationContext(), "未知错误");
                        break;
                }
            }

            @Override
            public void onFailure(Call<BaseData> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }

}
