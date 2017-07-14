package com.jsmosce.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.base.BaseData;
import com.jsmosce.data.DataSubmitChangePass;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//注册
public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.register_phone)
    EditText registerPhone;
    @Bind(R.id.register_code)
    EditText registerCode;
    @Bind(R.id.register_getcode)
    TextView registerGetcode;
    @Bind(R.id.register_password_first)
    EditText registerPasswordFirst;
    @Bind(R.id.register_password_senond)
    EditText registerPasswordSenond;
    @Bind(R.id.Login_login)
    Button LoginLogin;


    //验证码倒计时
    private int time = 0;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerGetcode.setOnClickListener(this);
        LoginLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_getcode:
                if (time > 0) {
                    Tools.myMakeText(getApplicationContext(), time + "秒之后，才能再次发送验证码");
                    return;
                }
                getDataCode();
                time = 59;
                setSecurity_Code();
                break;
            case R.id.Login_login:
                submit();
                break;

        }
    }

    public void submit() {
        DataSubmitChangePass dataSubmitChangePass = new DataSubmitChangePass();
        dataSubmitChangePass.setInterface("forgetPassword");
        dataSubmitChangePass.setPack("Login");
        if (Tools.isNull(registerPhone.getText().toString(), "电话不能为空", getApplicationContext())) {
            dataSubmitChangePass.setPhone(registerPhone.getText().toString());
        } else {
            return;
        }

        if (Tools.isNull(registerCode.getText().toString(), "验证码不能为空", getApplicationContext())) {
            dataSubmitChangePass.setCode(registerCode.getText().toString());
        } else {
            return;
        }

        if (Tools.isNull(registerPasswordFirst.getText().toString(), "新密码不能为空", getApplicationContext())) {
            dataSubmitChangePass.setPassword(registerPasswordFirst.getText().toString());
        } else {
            return;
        }

        if (!registerPasswordFirst.getText().toString().equals(registerPasswordSenond.getText().toString())) {
            Tools.myMakeText(getApplicationContext(), "密码需要一致");
        }

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Tools.toJson(dataSubmitChangePass));
        Call<BaseData> call = repo.postBaseData(body);

        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call,
                                   Response<BaseData> response) {

                switch (response.body().getState()) {
                    case 1:
                        Tools.myMakeText(getApplicationContext(), response.body().getMessage());
                        PasswordActivity.this.finish();
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


    public void getDataCode() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Login\",  \"type\":\"2\",\"Interface\":\"notify\", \"phone\":\"" + registerPhone.getText().toString().trim() + "\"}");
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
