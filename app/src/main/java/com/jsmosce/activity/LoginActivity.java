package com.jsmosce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jsmosce.MainActivity;
import com.jsmosce.R;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.SharedPMananger;
import com.jsmosce.Tools.Tools;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.data.DataUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//登录
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.login_phone)
    EditText loginPhone;
    @Bind(R.id.login_password)
    EditText loginPassword;
    @Bind(R.id.Login_login)
    Button LoginLogin;
    @Bind(R.id.Login_registered)
    Button LoginRegistered;
    @Bind(R.id.password)
    TextView password;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //  AndroidBug5497Workaround.assistActivity(this);
        //绑定activity
        ButterKnife.bind(this);
        LoginLogin.setOnClickListener(this);
        LoginRegistered.setOnClickListener(this);
        password.setOnClickListener(this);
    }

    public void getData() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Login\",\"Interface\":\"index\",\"Username\":" + loginPhone.getText().toString() + ",\"Password\":" + loginPassword.getText().toString() + "}");

        // RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Login\",\"Interface\":\"index\",\"Username\":15535730438,\"Password\":\"112233\"} ");

        Call<DataUser> call = repo.postFlyRoute(body);

        call.enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call,
                                   Response<DataUser> response) {
                try {
                    if (response.body().getState() != 1) {
                        Tools.myMakeText(getApplicationContext(), response.body().getMessage());
                    } else {
                        BaseApplication.user = response.body();
                        //  登陆成功 保存信息
                        SharedPMananger.putString(SharedPMananger.USER,
                                Tools.toJson(BaseApplication.user));
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                        LoginActivity.this.startActivity(intent);
                        LoginActivity.this.finish();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.Login_login:
                getData();
                break;
            case R.id.Login_registered:
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.password:
                intent.setClass(LoginActivity.this, PasswordActivity.class);
                startActivity(intent);
                break;

        }

    }

//    private static class ItemTypeAdapterFactory implements TypeAdapterFactory {
//
//        @Override
//        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
//            final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
//            final TypeAdapter<JsonElement> elementTypeAdapter = gson.getAdapter(JsonElement.class);
//
//            return new TypeAdapter<T>() {
//                @Override
//                public void write(JsonWriter out, T value) throws IOException {
//                    delegate.write(out, value);
//                }
//
//                //当获取了http请求的数据后，获得data所对应的值
//                @Override
//                public T read(JsonReader in) throws IOException {
//                    JsonElement jsonElement = elementTypeAdapter.read(in);
//
//                    if (jsonElement.isJsonObject()) {
//
//                        JsonObject jsonObject = jsonElement.getAsJsonObject();
//
//                        int code = jsonObject.get("State").getAsInt();
//                        if (jsonObject.has("Info")) {
//                            jsonElement = jsonObject.get("Info");
//                        }
//                    }
//
//                    return delegate.fromJsonTree(jsonElement);
//                }
//
//            }.nullSafe();
//        }
//    }


    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
           Tools.myMakeText(getApplicationContext(),"登录后才能使用本软件");
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }*/
}
