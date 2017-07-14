package com.jsmosce.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.Tools.DataCleanManager;
import com.jsmosce.Tools.FavorEvent;
import com.jsmosce.Tools.SharedPMananger;
import com.jsmosce.Tools.Tools;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.view.CustomizeDialog;
import com.jsmosce.view.DialogUpdate;
import com.jsmosce.view.TitleView;
import com.jsmosce.view.percentsuppor.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

//我的
public class MeActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.me_title)
    TitleView meTitle;
    //检查更新对话框
    Button updataNo;
    Button updataYes;
    AlertDialog alertDialog;
    //清除缓存对话框
    AlertDialog clear;
    PackageManager pm;
    //各个选项
    @Bind(R.id.home_userName)
    TextView homeUserName;
    @Bind(R.id.home_yiyuan)
    TextView homeYiyuan;
    @Bind(R.id.nav_gallery)
    PercentRelativeLayout navGallery;
    @Bind(R.id.nav_manage)
    PercentRelativeLayout navManage;
    @Bind(R.id.icon_updata)
    PercentRelativeLayout iconUpdata;
    @Bind(R.id.nav_feedback)
    PercentRelativeLayout navFeedback;
    @Bind(R.id.nav_camera)
    PercentRelativeLayout navCamera;
    @Bind(R.id.nav_exit)
    PercentRelativeLayout navExit;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        ButterKnife.bind(this);

        if (BaseApplication.user != null) {
            homeUserName.setText(BaseApplication.user.getInfo().getUsername());
            homeYiyuan.setText(BaseApplication.user.getInfo().getDepartmentName());
        }

        navGallery.setOnClickListener(this);
        navManage.setOnClickListener(this);
        iconUpdata.setOnClickListener(this);
        navFeedback.setOnClickListener(this);
        navCamera.setOnClickListener(this);
        navExit.setOnClickListener(this);

        File file = this.getCacheDir();
        if (clear == null) {
            try {
                TextView textView = new TextView(getApplicationContext());
                textView.setText("您的缓存大小为" + DataCleanManager.getCacheSize(file));
                textView.setTextSize(16);
                textView.setTextColor(ContextCompat.getColor(getApplication(), R.color.colorBlack));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 50, 0, 50);
                textView.setLayoutParams(lp);

                clear = new CustomizeDialog.Builder(this, R.style.Dialog).setTitle("清除缓存").setView(textView).setOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataCleanManager.cleanInternalCache(getApplicationContext());
                        Tools.myMakeText(getApplicationContext(), "缓存清除成功");
                        clear.dismiss();
                    }
                }).myCreate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        alertDialog =new DialogUpdate(this,R.style.DialogTwo);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //清除缓存Context.getExternalCacheDir()
            case R.id.nav_camera:


//                        clear = new AlertDialog.Builder(this, R.style.DialogTwo)
//                                .setTitle("清除缓存").setMessage("您的缓存大小为" + DataCleanManager.getCacheSize(file))
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        DataCleanManager.cleanInternalCache(getApplicationContext());
//                                        Tools.myMakeText(getApplicationContext(), "缓存清除成功");
//                                    }
//                                }).setNegativeButton("取消", null).show();

                clear.show();

                break;
            case R.id.nav_gallery:
                if (BaseApplication.user.getInfo().getRoleId() == 5) {
                    intent.setClass(MeActivity.this, HistoryDepartmentActivity.class);
                } else {
                    intent.setClass(MeActivity.this, HistoryActivity.class);
                }

                startActivity(intent);
                break;

            case R.id.nav_feedback:
                intent.setClass(MeActivity.this, FeedbackActivity.class);
                startActivity(intent);
                break;

            //更新
            case R.id.icon_updata:

                alertDialog.show();
                break;

            case R.id.nav_manage:
                intent.setClass(MeActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_exit:
                SharedPMananger.remove(SharedPMananger.USER);
                BaseApplication.user = null;

                Intent i = new Intent();
                i.setClass(MeActivity.this, LoginActivity.class);
                startActivity(i);

                FavorEvent anyEventType = new FavorEvent();
                anyEventType.setId(FavorEvent.Exit_App);
                EventBus.getDefault().postSticky(anyEventType);

                this.finish();
                break;
        }
    }


}
