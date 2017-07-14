package com.jsmosce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.base.BaseData;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//考案申请
public class TestApplicationActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.button_paichen)
    Button buttonPaichen;
    @Bind(R.id.button_sheji)
    Button buttonSheji;
    @Bind(R.id.button_pingfeng)
    Button buttonPingfeng;
    @Bind(R.id.button_dafeng)
    Button buttondafeng;
    @Bind(R.id.tv_zhuangtai)
    TextView tvZhuangtai;
    @Bind(R.id.button_kaosheng)
    Button buttonkasheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_application);
        ButterKnife.bind(this);
        buttonPaichen.setOnClickListener(this);
        buttonSheji.setOnClickListener(this);
        buttonPingfeng.setOnClickListener(this);
        buttondafeng.setOnClickListener(this);
        buttonkasheng.setOnClickListener(this);
        getData();
    }

    @Override
    protected void onStart() {
        super.onStart();

//        // for (int i : BaseApplication.flag) {
//        if (BaseApplication.flag[0] == 1) {
//            buttonPaichen.setBackgroundResource(R.color.colorGrayLight);
//        }
//        // }
//
//        if (BaseApplication.flag[1] == 1) {
//            buttonSheji.setBackgroundResource(R.color.colorGrayLight);
//        }
//
//        if (BaseApplication.flag[2] == 1) {
//            buttonPingfeng.setBackgroundResource(R.color.colorGrayLight);
//        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.button_paichen:
                intent.setClass(TestApplicationActivity.this, TestDesignActivity.class);
                startActivity(intent);
                break;
            case R.id.button_sheji:
                if (BaseApplication.caseId==0){
                    Tools.myMakeText(getApplicationContext(),"请先安排日程");
                    return;
                }
                //intent.setClass(TestApplicationActivity.this, CaseListActivity.class);
                intent.setClass(TestApplicationActivity.this, CaseItemActivity.class);
                startActivity(intent);
                break;
            case R.id.button_pingfeng:
                if (BaseApplication.caseId==0){
                    Tools.myMakeText(getApplicationContext(),"请先安排日程");
                    return;
                }
                intent.setClass(TestApplicationActivity.this, ScoreActivity.class);
                startActivity(intent);
                break;
            case R.id.button_dafeng:
                if (BaseApplication.caseId==0){
                    Tools.myMakeText(getApplicationContext(),"请先安排日程");
                    return;
                }
                intent.setClass(TestApplicationActivity.this, CandidatesListActivity.class);
                startActivity(intent);
                break;

            case R.id.button_kaosheng:
                if (BaseApplication.caseId==0){
                    Tools.myMakeText(getApplicationContext(),"请先安排日程");
                    return;
                }
                intent.setClass(TestApplicationActivity.this, CandidatesManagementActivity.class);
                startActivity(intent);
                break;
        }



    }

    public void getData() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        // RequestBody body = R
        // equestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Login\",\"Interface\":\"index\",\"Username\":" + loginPhone.getText().toString() + ",\"Password\":" + loginPassword.getText().toString() + "}");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Case\",\"Interface\":\"reason\",\"caseId\":" + BaseApplication.caseId + "}");

        Call<BaseData> call = repo.postBaseData(body);

        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call,
                                   Response<BaseData> response) {
                try {
                    tvZhuangtai.setText("考案Id为空或格式有误".equals(response.body().getMessage()) ? "新考案" : response.body().getMessage());

//                    if (response.body().getState() != 1) {
//                        Tools.myMakeText(getApplicationContext(), response.body().getMessage());
//                    } else {
//
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseData> call, Throwable t) {

            }
        });

    }
}
