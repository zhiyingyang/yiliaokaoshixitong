package com.jsmosce.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.jsmosce.R;
import com.jsmosce.Tools.FavorEvent;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.adapter.CandidatesListAdapter;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.data.DataStudentsList;

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
//考生列表
public class CandidatesListActivity extends BaseActivity {
    CandidatesListAdapter candidatesListAdapter;

    @Bind(R.id.candidateslist_header)
    RecyclerViewHeader candidateslistHeader;
    @Bind(R.id.exam_kaoanbianhao)
    TextView examKaoanbianhao;
    @Bind(R.id.exam_kaohedanwei)
    TextView examKaohedanwei;
    @Bind(R.id.exam_ceyanriqi)
    TextView examCeyanriqi;
    //  @Bind(R.id.exam_ceyanhexing)
    //  TextView examCeyanhexing;
    @Bind(R.id.exam_kaoanmingchen)
    TextView examKaoanmingchen;
    @Bind(R.id.exam_kaoguan)
    TextView examKaoguan;
    @Bind(R.id.exam_keshi)
    TextView examKeshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates_list);
        ButterKnife.bind(this);
        initView();
        getData();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(FavorEvent event) {
        //选择关联

        if (event.getId() == FavorEvent.Case_Sorceing) {
            candidatesListAdapter.remove(event.getPosition());
        }
    }

    public void initView() {
        RecyclerViewHeader recyclerViewHeader = (RecyclerViewHeader) findViewById(R.id.candidateslist_header);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.candidateslist_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        candidatesListAdapter = new CandidatesListAdapter(getApplicationContext());
        //设置Adapter
        recyclerView.setAdapter(candidatesListAdapter);
        recyclerViewHeader.attachTo(recyclerView);
    }


    public void getData() {
        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"Pack\":\"Check\",\"Interface\":\"students\",\"caseId\":" + BaseApplication.caseId + "}");
        Call<DataStudentsList> call = repo.postStudents(body);

        call.enqueue(new Callback<DataStudentsList>() {
            @Override
            public void onResponse(Call<DataStudentsList> call,
                                   Response<DataStudentsList> response) {

                DataStudentsList dataHomeTest = response.body();

                switch (dataHomeTest.getState()) {
                    case 1:
                        //添加数据
                        candidatesListAdapter.upData(dataHomeTest.getInfo().getUserInfo(), dataHomeTest.getInfo().getCaseInfo());
                        examKaoanbianhao.setText(examKaoanbianhao.getText() + dataHomeTest.getInfo().getCaseInfo().getNumber());
                        // examCeyanhexing.setText(examCeyanhexing.getText() + dataHomeTest.getInfo().getCaseInfo().getName());
                        examKaohedanwei.setText(examKaohedanwei.getText() + dataHomeTest.getInfo().getCaseInfo().getWork_unit());
                        examCeyanriqi.setText(examCeyanriqi.getText() + dataHomeTest.getInfo().getCaseInfo().getTextTime());
                        examKaoguan.setText(examKaoguan.getText() + BaseApplication.user.getInfo().getUsername());
                        examKaoanmingchen.setText(examKaoanmingchen.getText() + dataHomeTest.getInfo().getCaseInfo().getName());
                        examKeshi.setText(examKeshi.getText() + BaseApplication.user.getInfo().getDepartmentName());
                        break;
                    case 0:
                        Tools.myMakeText(getApplicationContext(), dataHomeTest.getMessage());
                        break;
                    case -1:
                        Tools.myMakeText(getApplicationContext(), "未知错误");
                        break;

                }
            }

            @Override
            public void onFailure(Call<DataStudentsList> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }


}
