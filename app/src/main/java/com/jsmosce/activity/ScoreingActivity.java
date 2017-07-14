package com.jsmosce.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.jsmosce.R;
import com.jsmosce.Tools.FavorEvent;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.adapter.ScoreingAdapter;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.base.BaseData;
import com.jsmosce.data.DataScoreing;
import com.jsmosce.data.DataStudentsList;
import com.jsmosce.data.DataSubmitScoreing;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScoreingActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.scoreing_rv)
    RecyclerView scoreingRv;
    @Bind(R.id.exam_kaoanbianhao)
    TextView examKaoanbianhao;
    @Bind(R.id.exam_kaohedanwei)
    TextView examKaohedanwei;
    @Bind(R.id.exam_ceyanriqi)
    TextView examCeyanriqi;
    //  @Bind(R.id.exam_ceyanhexing)
//  TextView examCeyanhexing;
    @Bind(R.id.exam_kaoanmingchen)
    TextView examKaoanmingchen;
    @Bind(R.id.exam_kaoguan)
    TextView examKaoguan;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.scoreing_bu)
    Button scoreingBu;
    ScoreingAdapter scoreingAdapter;

    DataStudentsList.InfoBean.CaseInfoBean caseInfoBean;
    DataStudentsList.InfoBean.UserInfoBean userInfoBean;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreing);
        ButterKnife.bind(this);
        initView();
        getData();
    }

    public void initView() {
        caseInfoBean = (DataStudentsList.InfoBean.CaseInfoBean) getIntent().getExtras().getSerializable("params");
        userInfoBean = (DataStudentsList.InfoBean.UserInfoBean) getIntent().getExtras().getSerializable("UserInfoBean");
        position = getIntent().getIntExtra("position", -1);
        RecyclerViewHeader recyclerViewHeader = (RecyclerViewHeader) findViewById(R.id.scoreing_header);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        scoreingRv.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        scoreingRv.setLayoutManager(layoutManager);
        scoreingAdapter = new ScoreingAdapter(getApplicationContext());
        //设置Adapter
        scoreingRv.setAdapter(scoreingAdapter);
        recyclerViewHeader.attachTo(scoreingRv);

        examKaoanbianhao.setText(examKaoanbianhao.getText() + caseInfoBean.getNumber());
        examKaohedanwei.setText(examKaohedanwei.getText() + caseInfoBean.getWork_unit());

        examCeyanriqi.setText(examCeyanriqi.getText() + caseInfoBean.getTextTime());

        examKaoguan.setText(examKaoguan.getText() + BaseApplication.user.getInfo().getUsername());
        examKaoanmingchen.setText(examKaoanmingchen.getText() + caseInfoBean.getName());
        scoreingBu.setOnClickListener(this);
    }


    public void getData() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Check\",\"Interface\":\"grade\",\"caseId\":" + BaseApplication.caseId + ",\"" +
                "studentId\":" + userInfoBean.getId() + ",\"userId\":" + BaseApplication.user.getInfo().getId() + "}");
        Call<DataScoreing> call = repo.postScoreing(body);

        call.enqueue(new Callback<DataScoreing>() {
            @Override
            public void onResponse(Call<DataScoreing> call,
                                   Response<DataScoreing> response) {

                DataScoreing dataScoreing = response.body();

                switch (dataScoreing.getState()) {
                    case 1:
                        //添加数据
                        scoreingAdapter.upData(dataScoreing.getInfo().getCate(), dataScoreing.getInfo().getPoints());

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
            public void onFailure(Call<DataScoreing> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scoreing_bu:
                submitData();
                break;
        }
    }


    public void submitData() {
        DataSubmitScoreing dataSubmitScoreing = new DataSubmitScoreing();
        dataSubmitScoreing.setInterface("gradeSubmit");
        dataSubmitScoreing.setPack("Check");
        dataSubmitScoreing.setCaseId(Integer.parseInt(caseInfoBean.getId()));
        dataSubmitScoreing.setStudentId(Integer.parseInt(userInfoBean.getId()));
        dataSubmitScoreing.setUserId(Integer.parseInt(BaseApplication.user.getInfo().getId()));
        dataSubmitScoreing.setScoreInfo(scoreingAdapter.getDataSubmitScoreing());

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"
        ), Tools.toJson(dataSubmitScoreing));
        Call<BaseData> call = repo.postBaseData(body);

        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call,
                                   Response<BaseData> response) {

                BaseData baseData = response.body();

                switch (baseData.getState()) {
                    case 1:
                        Tools.myMakeText(getApplicationContext(), "提交成功");
                        FavorEvent anyEventType = new FavorEvent();
                        anyEventType.setId(FavorEvent.Case_Sorceing);
                        anyEventType.setPosition(position);
                        EventBus.getDefault().postSticky(anyEventType);
                        ScoreingActivity.this.finish();
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
