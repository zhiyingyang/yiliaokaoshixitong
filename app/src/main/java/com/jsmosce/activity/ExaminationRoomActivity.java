package com.jsmosce.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.jsmosce.R;
import com.jsmosce.Tools.FavorEvent;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.adapter.SelectRoomItemAdapter;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.data.DataSelectRoom;
import com.jsmosce.view.TitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
//选择考场
public class ExaminationRoomActivity extends BaseActivity {

    @Bind(R.id.room_title)
    TitleView roomTitle;
    @Bind(R.id.select_room_am)
    RecyclerView selectRoomAm;
    @Bind(R.id.select_room_pm)
    RecyclerView selectRoomPm;
    private String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_room);
        ButterKnife.bind(this);
        dateString = getIntent().getStringExtra("date");
        initView();
        EventBus.getDefault().register(this);
    }


    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        selectRoomAm.setHasFixedSize(true);
        selectRoomAm.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);


        LinearLayoutManager layoutManagerPm = new LinearLayoutManager(this);
        layoutManagerPm.setOrientation(OrientationHelper.VERTICAL);
        //设置布局管理器
        selectRoomPm.setHasFixedSize(true);
        selectRoomPm.setLayoutManager(layoutManagerPm);
        getData();
    }

    @Subscribe
    public void onEvent(FavorEvent event) {
        //选择关联
        if (event.getId() == FavorEvent.Select_Room) {
            this.finish();
        }
    }

    public void getData() {

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Case\",\"Interface\":\"timeSelect\",\"departmentId\":\""+ BaseApplication.user.getInfo().getDepartmentId()+"\",\"date\":\"" + dateString + "\"}");
        Call<DataSelectRoom> call = repo.postSelectRoom(body);

        call.enqueue(new Callback<DataSelectRoom>() {
            @Override
            public void onResponse(Call<DataSelectRoom> call,
                                   Response<DataSelectRoom> response) {
                try {
                    if (response.body().getState() != 1) {
                        Tools.myMakeText(getApplicationContext(), response.body().getMessage());
                    } else {
                        SelectRoomItemAdapter selectRoomItemAdapterAm = new SelectRoomItemAdapter(getApplicationContext());
                        selectRoomItemAdapterAm.addData(response.body().getInfo().getAm());
                        selectRoomAm.setAdapter(selectRoomItemAdapterAm);
                        SelectRoomItemAdapter selectRoomItemAdapterPm = new SelectRoomItemAdapter(getApplicationContext());
                        selectRoomItemAdapterPm.addData(response.body().getInfo().getPm());
                        selectRoomPm.setAdapter(selectRoomItemAdapterPm);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataSelectRoom> call, Throwable t) {

            }
        });

    }


}
