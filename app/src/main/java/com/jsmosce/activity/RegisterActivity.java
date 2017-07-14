package com.jsmosce.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseData;
import com.jsmosce.data.DataHospital;
import com.jsmosce.data.DataSubmitRegister;
import com.jsmosce.view.DialogRegister;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.register_name)
    EditText registerName;
    @Bind(R.id.register_phone)
    EditText registerPhone;
    @Bind(R.id.register_code)
    EditText registerCode;
    @Bind(R.id.register_danwei)
    TextView registerDanwei;
    @Bind(R.id.register_keshi)
    TextView registerKeshi;
    @Bind(R.id.register_zhiwei)
    EditText registerZhiwei;
    @Bind(R.id.register_submit)
    Button submit;
    DataHospital dataHospital;
    @Bind(R.id.register_getcode)
    TextView registerGetcode;

    private int hospitalId;
    private int DepartmentInfoId;

    //验证码倒计时
    private int time = 0;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ButterKnife.bind(this);
        registerDanwei.setOnClickListener(this);
        registerGetcode.setOnClickListener(this);
        submit.setOnClickListener(this);

        registerKeshi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.register_danwei:
                getData();
                break;
            case R.id.register_getcode:
                if (DepartmentInfoId == 0) {
                    Tools.myMakeText(getApplicationContext(), "请选择科室");
                    return;
                }
                if (time > 0) {
                    Tools.myMakeText(getApplicationContext(), time + "秒之后，才能再次发送验证码");
                    return;
                }
                getDataCode();
                time = 59;
                setSecurity_Code();
                break;
            case R.id.register_submit:
                submit();
                break;
            //请先选择医院
            case R.id.register_keshi:
                if (dataHospital == null || dataHospital.getDepartmentInfo() == null) {
                    Tools.myMakeText(getApplicationContext(), "请先选择单位");
                    return;
                }

                new DialogRegister(RegisterActivity.this,R.style.Dialog).addDatas(dataHospital.getDepartmentInfo(), "选择科室", new DialogRegister.getData() {
                    @Override
                    public void getData(String string, int id) {
                        DepartmentInfoId = id;
                        registerKeshi.setText(string);
                    }
                }).show();

                break;
        }
    }


    public void getData() {
        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Login\",\"Interface\":\"register\"}");
        Call<DataHospital> call = repo.postHospital(body);

        call.enqueue(new Callback<DataHospital>() {
            @Override
            public void onResponse(Call<DataHospital> call,
                                   Response<DataHospital> response) {

                dataHospital = response.body();

                switch (dataHospital.getState()) {
                    case 1:
                        new DialogRegister(RegisterActivity.this,R.style.Dialog).addDatas(dataHospital.getHospitalInfo(), "选择医院", new DialogRegister.getData() {
                            @Override
                            public void getData(String string, int id) {
                                hospitalId = id;
                                registerDanwei.setText(string);
                                getDataDepartmentInfoId();
                            }
                        }).show();

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

    public void getDataDepartmentInfoId() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Login\",\"Interface\":\"hospital\",\"hospitalId\":" + hospitalId + "}");
        Call<DataHospital> call = repo.postHospital(body);

        call.enqueue(new Callback<DataHospital>() {
            @Override
            public void onResponse(Call<DataHospital> call,
                                   Response<DataHospital> response) {
                dataHospital = response.body();
                switch (dataHospital.getState()) {
                    case 1:
                        if (dataHospital.getDepartmentInfo() == null) {
                            Tools.myMakeText(getApplication(), "您所在的医院暂未添加科室无法注册,请联系管理员");
                            return;
                        }
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

    public void getDataCode() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Login\", \"type\":\"1\",\"Interface\":\"notify\", \"phone\":\"" + registerPhone.getText().toString().trim() + "\"}");
        Call<BaseData> call = repo.postBaseData(body);

        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call,
                                   Response<BaseData> response) {

                switch (response.body().getState()) {

                    case 1:
                        Tools.myMakeText(getApplicationContext(), response.body().getMessage());
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

    public void submit() {

        DataSubmitRegister dataSubmitRegister = new DataSubmitRegister();
        dataSubmitRegister.setInterface("userAdd");
        dataSubmitRegister.setPack("Login");
        if (Tools.isNull(registerPhone.getText().toString(), "电话不能为空", getApplicationContext())) {
            dataSubmitRegister.setPhone(registerPhone.getText().toString());
        } else {
            return;
        }

        if (Tools.isNull(registerCode.getText().toString(), "验证码不能为空", getApplicationContext())) {
            dataSubmitRegister.setCode(registerCode.getText().toString());
        } else {
            return;
        }

        if (Tools.isNull(registerDanwei.getText().toString(), "单位不能为空", getApplicationContext())) {
            dataSubmitRegister.setHospitalId(hospitalId);
        } else {
            return;
        }

        if (Tools.isNull(registerKeshi.getText().toString(), "科室不能为空", getApplicationContext())) {
            dataSubmitRegister.setDepartmentId(DepartmentInfoId);
        } else {
            return;
        }

        if (Tools.isNull(registerZhiwei.getText().toString(), "职位不能为空", getApplicationContext())) {
            dataSubmitRegister.setRole(registerZhiwei.getText().toString());
        } else {
            return;
        }

        if (Tools.isNull(registerName.getText().toString(), "用户名不能为空", getApplicationContext())) {
            dataSubmitRegister.setUsername(registerName.getText().toString());
        } else {
            return;
        }


        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Tools.toJson(dataSubmitRegister));
        Call<BaseData> call = repo.postBaseData(body);

        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call,
                                   Response<BaseData> response) {

                switch (response.body().getState()) {
                    case 1:
                        Tools.myMakeText(getApplicationContext(), response.body().getMessage());
                        RegisterActivity.this.finish();
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
                Tools.myMakeText(RegisterActivity.this.getApplicationContext(), t.getMessage());
            }
        });
    }


    /**
     * 获取验证码的定时器
     **/
    public void setSecurity_Code() {
        runnable = new Runnable() {
            public void run() {
                try {
                    time--;
                    handler.sendMessage(handler.obtainMessage(0));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        handler = new Handler() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (time == 0) {
                    registerGetcode.setText("获取验证码");
                    // registerGetcode.setTextColor(getResources().getColor(R.color.colorWhite, null));
                    // registerGetcode.setBackgroundColor(getResources().getColor(R.color.colorGrayLight, null));
                    time = 59;
                } else {
                    //registerGetcode.setBackgroundColor(getResources().getColor(R.color.colorWhite, null));
                    //registerGetcode.setTextColor(getResources().getColor(R.color.colorBlack, null));
                    registerGetcode.setText("重新获取(" + time + ")");
                    handler.postDelayed(runnable, 1000);
                }
            }
        };

        handler.postDelayed(runnable, 1000);
    }

    public void removeRunnable() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler.removeMessages(-1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeRunnable();
    }
}
