package com.jsmosce;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jsmosce.Tools.FavorEvent;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.activity.LoginActivity;
import com.jsmosce.activity.MeActivity;
import com.jsmosce.activity.TestApplicationActivity;
import com.jsmosce.adapter.HomeAdapter;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.data.DataHomeTest;
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

//首页
public class MainActivity extends BaseActivity
        implements View.OnClickListener, TitleView.RightOnClick, MaterialRefreshListener {
    DrawerLayout drawer;
    MaterialRefreshLayout materialRefreshLayout;
    HomeAdapter homeAdapter;
    @Bind(R.id.home_title)
    TitleView homeTitle;
    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        findViewById(R.id.home_close).setOnClickListener(this);
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.home_refresh);

//        for (int i = 0; i < 10; i++) {
//            dataTestCases.add(new DataUser());
//        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        TitleView titleView = (TitleView) findViewById(R.id.home_title);
        // titleView.setBackNull();
        titleView.setRightOnClick(this);
        // drawer.addDrawerListener(this);
        initView();
        materialRefreshLayout.autoRefresh();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //
    }

    public void initView() {
        // RecyclerViewHeader recyclerViewHeader = (RecyclerViewHeader) findViewById(R.id.home_header);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.show_evaluation_eva);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        homeAdapter = new HomeAdapter(getApplicationContext());
        //设置Adapter
        recyclerView.setAdapter(homeAdapter);
        // recyclerViewHeader.attachTo(recyclerView);

        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.home_refresh);
        materialRefreshLayout.setMaterialRefreshListener(this);
        findViewById(R.id.buttonFab).setOnClickListener(this);
        homeTitle.setRightImg(R.drawable.icon_user);
        if (BaseApplication.user != null) {
            homeTitle.setTitle(BaseApplication.user.getInfo().getWork_unitName());
        }
    }

    public void getData() {
        if (BaseApplication.user == null) return;
        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Case\",\"Interface\":\"index\",\"page\":" + page + ",\"UserId\":" + BaseApplication.user.getInfo().getId() + "}");
        Call<DataHomeTest> call = repo.postHomeTestRoute(body);

        call.enqueue(new Callback<DataHomeTest>() {
            @Override
            public void onResponse(Call<DataHomeTest> call,
                                   Response<DataHomeTest> response) {

                DataHomeTest dataHomeTest = response.body();

                switch (dataHomeTest.getState()) {
                    case 1:
                        if (page == 0) {
                            homeAdapter.upData(dataHomeTest.getInfo());
                        } else {
                            homeAdapter.addData(dataHomeTest.getInfo());
                        }

                        break;
                    case 0:
                        if (page > 0) {
                            page--;
                        }
                        Tools.myMakeText(getApplicationContext(), dataHomeTest.getMessage());
                        break;
                    case -1:
                        if (page > 0) {
                            page--;
                        }
                        Tools.myMakeText(getApplicationContext(), "未知错误");
                        break;

                }

                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        materialRefreshLayout.finishRefresh();
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                }.sendEmptyMessageDelayed(0, 1000);


            }

            @Override
            public void onFailure(Call<DataHomeTest> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        materialRefreshLayout.finishRefresh();
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }
        });

    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//
//        } else if (id == R.id.nav_manage) {
//
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.END);
//        return true;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_title_right_LinearLayout:

                Intent intent2;
                if (BaseApplication.user == null) {
                    intent2 = new Intent(this, LoginActivity.class);
                    startActivity(intent2);
                    return;
                }

                intent2 = new Intent(this, MeActivity.class);
                startActivity(intent2);

                break;
            case R.id.buttonFab:
                BaseApplication.caseId = 0;
                Intent intent;
                if (BaseApplication.user == null) {
                    intent = new Intent(this, LoginActivity.class);
                } else {
                    intent = new Intent(this, TestApplicationActivity.class);
                }
                startActivity(intent);
                break;

        }

    }

    @Override
    public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
        page = 0;
        getData();
    }

    @Override
    public void onfinish() {

    }

    @Override
    public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
        page++;
        getData();
    }


    @Subscribe
    public void onEvent(FavorEvent event) {
        //选择关联

        if (event.getId() == FavorEvent.Exit_App) {
            this.finish();
        }
        if (event.getId() == FavorEvent.RefreshMainActivity) {
            materialRefreshLayout.autoRefresh();
        }
    }


}
