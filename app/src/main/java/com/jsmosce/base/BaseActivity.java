package com.jsmosce.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jsmosce.Tools.SharedPMananger;
import com.jsmosce.Tools.Tools;
import com.jsmosce.data.DataUser;

import org.greenrobot.eventbus.EventBus;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BaseApplication.user == null) {
            //恢复登陆信息
            try {
                BaseApplication.user = Tools.toObject(
                        SharedPMananger.getString(SharedPMananger.USER, ""),
                        DataUser.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
