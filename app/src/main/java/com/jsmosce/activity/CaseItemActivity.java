package com.jsmosce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import com.jsmosce.R;
import com.jsmosce.Tools.FavorEvent;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.adapter.RecycleItemTouchHelper;
import com.jsmosce.adapter.ScoreAdapter;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.base.BaseData;
import com.jsmosce.data.DataCaseDesign;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

//考案设计
public class CaseItemActivity extends BaseActivity implements View.OnClickListener {
    ScoreAdapter scoreAdapter;
    @Bind(R.id.score_add)
    Button scoreAdd;
    @Bind(R.id.score_updata)
    Button scoreUpdata;
    RecyclerView recyclerView;
//    @Bind(R.id.fab)
//    FloatingActionMenu fab;

    //private List
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        initView();
        getTestData();
        EventBus.getDefault().register(this);
    }

    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.score_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        scoreAdapter = new ScoreAdapter(getApplicationContext());

        List<DataCaseDesign.DesignInfoBean> ds = new ArrayList<DataCaseDesign.DesignInfoBean>();

        // 取xml文件格式的字符数组
        String[] good = getResources().getStringArray(R.array.score_item);
        for (String str : good) {
            DataCaseDesign.DesignInfoBean dataScoreItem = new DataCaseDesign.DesignInfoBean();
            dataScoreItem.setName(str);
            ds.add(dataScoreItem);
        }

        scoreAdapter.addData(ds);
        //设置Adapter
        recyclerView.setAdapter(scoreAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecycleItemTouchHelper(scoreAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        scoreAdd.setOnClickListener(this);
        scoreUpdata.setOnClickListener(this);
    }


    @Subscribe
    public void onEvent(FavorEvent event) {
      //  fab.close(true);
        //选择关联
        if (event.getId() == FavorEvent.Case_Detail) {
            DataCaseDesign.DesignInfoBean designInfoBean = (DataCaseDesign.DesignInfoBean) event.getObject();
            scoreAdapter.changeData(designInfoBean, designInfoBean.getOrderby());
        }

        if (event.getId() == FavorEvent.Case_add) {
            DataCaseDesign.DesignInfoBean designInfoBean = (DataCaseDesign.DesignInfoBean) event.getObject();
            scoreAdapter.addDataOne(designInfoBean);
            recyclerView.scrollToPosition(scoreAdapter.getItemCount() - 1);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.score_add:
                Intent intent = new Intent(getApplicationContext(), ExamDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);

                break;

            case R.id.score_updata:
                submitData();
                break;
        }
    }

    public void getTestData() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Case\",\"Interface\":\"caseDesign\",\"caseId\":" + BaseApplication.caseId + "}");
        Call<DataCaseDesign> call = repo.postCaseItem(body);

        call.enqueue(new Callback<DataCaseDesign>() {
            @Override
            public void onResponse(Call<DataCaseDesign> call,
                                   Response<DataCaseDesign> response) {

                DataCaseDesign dataCaseDesign = response.body();

                switch (dataCaseDesign.getState()) {
                    case 1:
                        scoreAdapter.upData(dataCaseDesign.getDesignInfo());
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
            public void onFailure(Call<DataCaseDesign> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }


    public void submitData() {

        DataCaseDesign dataCaseDesign = new DataCaseDesign();
        dataCaseDesign.setPack("Case");
        dataCaseDesign.setInterface("addcaseDesign");
        dataCaseDesign.setCaseId(BaseApplication.caseId);
        dataCaseDesign.setDesignInfo(scoreAdapter.getDatas());

        for (int i = 0; i < scoreAdapter.getDatas().size(); i++) {
            scoreAdapter.getDatas().get(i).setOrderby(i);
        }

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Tools.toJson(dataCaseDesign));
        Call<BaseData> call = repo.postBaseData(body);

        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData>  call,
                                   Response<BaseData> response) {
                switch (response.body().getState()) {
                    case 1:
                        Tools.myMakeText(getApplicationContext(), "提交成功");
                        CaseItemActivity.this.finish();
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
            public void onFailure(Call<BaseData> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }


}
