package com.jsmosce.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jsmosce.R;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
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

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.change_password_oid)
    EditText changePasswordOid;
    @Bind(R.id.change_password_first)
    EditText changePasswordFirst;
    @Bind(R.id.change_password_senond)
    EditText changePasswordSenond;
    @Bind(R.id.change_password_submit)
    Button changePasswordSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        changePasswordSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        submit();
    }

    public void submit() {

        if (!Tools.isNull(changePasswordOid.getText().toString(), "旧密码不能为空", getApplicationContext()))
            return;

        if (changePasswordFirst.getText().toString().equals(changePasswordOid.getText().toString())) {
            Tools.myMakeText(getApplicationContext(), "新密码不能与旧密码相同");
            return;
        }

        if (!changePasswordSenond.getText().toString().equals(changePasswordFirst.getText().toString())) {
            Tools.myMakeText(getApplicationContext(), "密码需要一致");
            return;
        }

        DataSubmitChangePassword dataSubmitChangePassword = new DataSubmitChangePassword();
        dataSubmitChangePassword.setInterface("changePassword");
        dataSubmitChangePassword.setPack("Login");
        dataSubmitChangePassword.setUserId(BaseApplication.user.getInfo().getId());
        dataSubmitChangePassword.setNewpassword(changePasswordSenond.getText().toString());
        dataSubmitChangePassword.setOldpassword(changePasswordOid.getText().toString());

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Tools.toJson(dataSubmitChangePassword));
        Call<BaseData> call = repo.postBaseData(body);

        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call,
                                   Response<BaseData> response) {

                switch (response.body().getState()) {
                    case 1:
                        Tools.myMakeText(getApplicationContext(), response.body().getMessage());
                        ChangePasswordActivity.this.finish();
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

    class DataSubmitChangePassword {

        /**
         * Pack : Login
         * Interface : changePassword
         * userId : 1
         * newpassword : 112233
         * oldpassword : yanrui123
         */

        private String Pack;
        private String Interface;
        private String userId;
        private String newpassword;
        private String oldpassword;

        public String getPack() {
            return Pack;
        }

        public void setPack(String Pack) {
            this.Pack = Pack;
        }

        public String getInterface() {
            return Interface;
        }

        public void setInterface(String Interface) {
            this.Interface = Interface;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNewpassword() {
            return newpassword;
        }

        public void setNewpassword(String newpassword) {
            this.newpassword = newpassword;
        }

        public String getOldpassword() {
            return oldpassword;
        }

        public void setOldpassword(String oldpassword) {
            this.oldpassword = oldpassword;
        }
    }
}
