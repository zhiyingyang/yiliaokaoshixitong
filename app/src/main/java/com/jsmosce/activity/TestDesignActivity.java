package com.jsmosce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.Tools.AndroidBug5497Workaround;
import com.jsmosce.Tools.FavorEvent;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.base.BaseData;
import com.jsmosce.data.DataSelectDate;
import com.jsmosce.data.DataSelectRoom;
import com.jsmosce.data.DataTestDesign;
import com.jsmosce.view.DialogDate;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//排程设计
public class TestDesignActivity extends BaseActivity implements OnDateSelectedListener, View.OnClickListener {


    @Bind(R.id.kaoanhao)
    TextView kaoanhao;
    @Bind(R.id.test_name)
    EditText testName;
    @Bind(R.id.test_user_name)
    EditText testUserName;
    @Bind(R.id.yuding_time)
    TextView yudingTime;
    @Bind(R.id.ceyan_tiem)
    TextView ceyanTiem;
    @Bind(R.id.test_room)
    TextView testRoom;
    @Bind(R.id.test_submit)
    Button testSubmit;
    @Bind(R.id.test_jump)
    Button testJump;

    private int flag = 0;
    //选中的考场号
    private Object room;
    //选择时间的dialog
    private DialogDate dialogDate;
    //考案id
    private String testId;
    //保存数据
    private DataTestDesign dataTestDesign;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_design);
        ButterKnife.bind(this);
        dialogDate = new DialogDate(this, this);
        yudingTime.setOnClickListener(this);
        ceyanTiem.setOnClickListener(this);
        EventBus.getDefault().register(this);

        testId = BaseApplication.user.getInfo().getWork_unitId() + "" + BaseApplication.user.getInfo().getDepartmentId() + "" + new Date().getTime();

        testSubmit.setOnClickListener(this);
        testJump.setOnClickListener(this);
        testRoom.setOnClickListener(this);

        AndroidBug5497Workaround.assistActivity(this);
        if (BaseApplication.caseId > 0) {

            getTestData();
        } else {
            kaoanhao.setText("考案号:" + testId);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yuding_time:
                flag = 0;
                //getData();
                dialogDate.show();
                break;
            case R.id.ceyan_tiem:
                flag = 1;
                getData();
                break;
            case R.id.test_submit:

                submitData();

                break;
            case R.id.test_jump:
                Intent intent = new Intent(TestDesignActivity.this, ArticleActivity.class);
                intent.putExtra("rId", BaseApplication.caseId);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;

            case R.id.test_room:
                Intent intentRoom = new Intent();
                intentRoom.setClass(TestDesignActivity.this, ExaminationRoomActivity.class);
                intentRoom.putExtra("date", ceyanTiem.getText().toString());
                startActivity(intentRoom);

                break;
        }
    }

    public void getData() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Case\",\"Interface\":\"add\",\"departmentId\":" + BaseApplication.user.getInfo().getDepartmentId() + "} ");
        Call<DataSelectDate> call = repo.postSelectDate(body);

        call.enqueue(new Callback<DataSelectDate>() {
            @Override
            public void onResponse(Call<DataSelectDate> call,
                                   Response<DataSelectDate> response) {
                try {
                    if (response.body().getState() != 1) {
                        Tools.myMakeText(getApplicationContext(), response.body().getMessage());
                    } else {
                        if (response.body().getInfo() == null) {
                            dialogDate.upDate(new ArrayList<String>());
                        } else {
                            dialogDate.upDate(response.body().getInfo().getDate());
                        }
                        dialogDate.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataSelectDate> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }

    @Subscribe
    public void onEvent(FavorEvent event) {
        //选择关联
        if (event.getId() == FavorEvent.Select_Room) {
            room = event.getObject();
            if (room instanceof DataSelectRoom.InfoBean.PmBean) {
                testRoom.setText("下午；" + ((DataSelectRoom.InfoBean.PmBean) room).getName());
            } else {
                testRoom.setText("上午；" + ((DataSelectRoom.InfoBean.AmBean) room).getName());

            }

        }
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        // Tools.myMakeText(getApplicationContext(), date.getDate().toString());


        if (flag == 0) {
            yudingTime.setText(sdf.format(date.getDate()));
        } else {
            ceyanTiem.setText(sdf.format(date.getDate()));
        }

        dialogDate.dismiss();
    }

    //获取旧数据
    public void getTestData() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Case\",\"Interface\":\"arrange\",\"caseId\":" + BaseApplication.caseId + "}");
        Call<DataTestDesign> call = repo.postTestDesign(body);

        call.enqueue(new Callback<DataTestDesign>() {
            @Override
            public void onResponse(Call<DataTestDesign> call,
                                   Response<DataTestDesign> response) {

                dataTestDesign = response.body();

                switch (dataTestDesign.getState()) {
                    case 1:

                        testName.setText(dataTestDesign.getCaseInfo().getName());
                        testUserName.setText(dataTestDesign.getCaseInfo().getCharge());
                        yudingTime.setText(dataTestDesign.getCaseInfo().getFirstdoneTime());
                        ceyanTiem.setText(dataTestDesign.getCaseInfo().getTextTime());

                        testId = dataTestDesign.getCaseInfo().getNumber();
                        kaoanhao.setText("考案号:" + testId);
                        if ("1".equals(dataTestDesign.getCaseInfo().getAm_pm())) {
                            testRoom.setText("上午；" + dataTestDesign.getCaseInfo().getExam_room());
                        } else {
                            testRoom.setText("下午；" + dataTestDesign.getCaseInfo().getExam_room());
                        }

                        break;
                    case 0:
                        Tools.myMakeText(getApplicationContext(), dataTestDesign.getMessage());
                        break;
                    case -1:
                        Tools.myMakeText(getApplicationContext(), "未知错误");
                        break;

                }
            }

            @Override
            public void onFailure(Call<DataTestDesign> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }

    //提交数据
    public void submitData() {
        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        SubmitData submitData = new SubmitData();
        submitData.setPack("Case");
        submitData.setInterface("addCase");
        submitData.setName(testName.getText().toString());
        submitData.setCharge(testUserName.getText().toString());
        submitData.setFirstdoneTime(yudingTime.getText().toString());
        submitData.setTextTime(ceyanTiem.getText().toString());
        submitData.setUserId(BaseApplication.user.getInfo().getId());
        submitData.setNumber(testId);

        if (BaseApplication.caseId > 0) {
            submitData.setRId(dataTestDesign.getCaseInfo().getRId());
            submitData.setAm_pm(dataTestDesign.getCaseInfo().getAm_pm());
            submitData.setCaseId(BaseApplication.caseId + "");
        } else {
            if (room == null) {
                Tools.myMakeText(getApplicationContext(), "必须选择考场");
            }
            if (room instanceof DataSelectRoom.InfoBean.PmBean) {
                submitData.setRId(((DataSelectRoom.InfoBean.PmBean) room).getId());
                submitData.setAm_pm("1");
            } else {
                submitData.setRId(((DataSelectRoom.InfoBean.AmBean) room).getId());
                submitData.setAm_pm("2");
            }
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Tools.toJson(submitData));
        Call<BaseData> call = repo.postBaseData(body);

        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call,
                                   Response<BaseData> response) {

                switch (response.body().getState()) {
                    case 1:
                        try {
                            Tools.myMakeText(getApplicationContext(), response.body().getMessage());
                            if (response.body().getMessage().equals("修改成功")) {
                                Intent intent = new Intent(TestDesignActivity.this, ArticleActivity.class);
                                intent.putExtra("type", 1);
                                //  BaseApplication.caseId = Integer.parseInt(response.body().getInfo().toString());
                                startActivity(intent);
                                TestDesignActivity.this.finish();
                            } else {
                                Intent intent = new Intent(TestDesignActivity.this, ArticleActivity.class);
                                intent.putExtra("rId", response.body().getInfo().toString());
                                intent.putExtra("type", 1);
                                BaseApplication.caseId = Integer.parseInt(response.body().getInfo().toString());
                                startActivity(intent);
                                TestDesignActivity.this.finish();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        FavorEvent anyEventType = new FavorEvent();
                        anyEventType.setId(FavorEvent.RefreshMainActivity);
                        EventBus.getDefault().postSticky(anyEventType);

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


    public static class SubmitData {


        /**
         * Pack : Case
         * Interface : addCase
         * textTime : 2017-05-31
         * name : 考案名称接口
         * number : kaoanhao001
         * charge : fuzeren
         * firstdoneTime : 2017-05-28
         * rId : 1
         * am_pm : 2
         * userId : 1
         */

        private String Pack;
        private String Interface;
        private String textTime;
        private String name;
        private String number;
        private String charge;
        private String firstdoneTime;
        private String rId;
        private String am_pm;
        private String userId;
        private String caseId;

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

        public String getTextTime() {
            return textTime;
        }

        public void setTextTime(String textTime) {
            this.textTime = textTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }

        public String getFirstdoneTime() {
            return firstdoneTime;
        }

        public void setFirstdoneTime(String firstdoneTime) {
            this.firstdoneTime = firstdoneTime;
        }

        public String getRId() {
            return rId;
        }

        public void setRId(String rId) {
            this.rId = rId;
        }

        public String getAm_pm() {
            return am_pm;
        }

        public void setAm_pm(String am_pm) {
            this.am_pm = am_pm;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCaseId() {
            return caseId;
        }

        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }
    }

}
