package com.jsmosce.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.jsmosce.R;
import com.jsmosce.Tools.GitHubApi;
import com.jsmosce.Tools.RetrofitClient;
import com.jsmosce.Tools.Tools;
import com.jsmosce.adapter.CandidatesAllAdapter;
import com.jsmosce.base.BaseApplication;
import com.jsmosce.base.BaseData;
import com.jsmosce.data.DataAllCandiates;
import com.jsmosce.view.CustomizeDialog;
import com.jsmosce.view.TitleView;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CandidatesAllListActivity extends AppCompatActivity implements TitleView.RightOnClick {
    CandidatesAllAdapter CandidatesAllAdapter;
    @Bind(R.id.candidates_all_rv)
    RecyclerView candidatesAllRv;
    @Bind(R.id.searchView)
    SearchView searchView;
    @Bind(R.id.candidates_title)
    TitleView candidatesTitle;
    //添加新的学员
    AlertDialog dialog;
    EditText name;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates_all_list);
        ButterKnife.bind(this);
        initView();
        candidatesTitle.setRightOnClick(this);
        candidatesTitle.setRightImg(R.drawable.icon_add_student);
    }


    public void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        candidatesAllRv.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        candidatesAllRv.setLayoutManager(layoutManager);
        CandidatesAllAdapter = new CandidatesAllAdapter(getApplicationContext());

        //设置Adapter
        candidatesAllRv.setAdapter(CandidatesAllAdapter);
        CandidatesAllAdapter.addData((List<DataAllCandiates.InfoBean.StudentInfoBean>) getIntent().getSerializableExtra("StudentInfo"));



//        //获取到TextView的ID
//        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
////获取到TextView的控件
//        TextView textView = (TextView) searchView.findViewById(id);
////设置字体大小为14sp
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);//14sp

//        android.support.v7.widget.SearchView.SearchAutoComplete textView = (android.support.v7.widget.SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
//        textView.setTextSize(16);
        // 设置搜索文本监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                CandidatesAllAdapter.filter(newText);
                return false;
            }
        });


        //AlertDialog
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_edit, null);

        name = (EditText) layout.findViewById(R.id.dengji);
        phone = (EditText) layout.findViewById(R.id.qujian);
        name.setHint("请输入学员姓名");
        phone.setHint("请输入手机号");
        phone.setInputType(InputType.TYPE_CLASS_PHONE);

        dialog = new CustomizeDialog.Builder(this, R.style.Dialog).setTitle("添加一个新的学员").setView(layout).setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(name.getText()) || "".equals(phone.getText())) {
                    Tools.myMakeText(getApplicationContext(), "内容不能为空");
                    return;
                }
                submitNewStudent();
                dialog.dismiss();
                name.setText("");
                phone.setText("");
            }
        }).myCreate();


        // dialog = new AlertDialog.Builder(this, R.style.DialogTwo)
//                .setTitle("添加一个新的学员")
//                .setView(layout)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if ("".equals(name.getText()) || "".equals(phone.getText())) {
//                            Tools.myMakeText(getApplicationContext(), "内容不能为空");
//                            return;
//                        }
//                        submitNewStudent();
//                    }
//                }).setNegativeButton("取消", null).create();
    }

    //向上个界面返回数据
    @Override
    public void finish() {
        Intent intent = new Intent(CandidatesAllListActivity.this, CandidatesManagementActivity.class);
        intent.putExtra("datas", (Serializable) CandidatesAllAdapter.getDatas());
        setResult(RESULT_OK, intent);
        super.finish();
    }


    @Override
    public void onClick(View view) {
        dialog.show();
    }

    //添加新学员
    public void submitNewStudent() {

        newStudent newStudent = new newStudent();
        newStudent.setInterface("addStudent");
        newStudent.setPack("Case");
        newStudent.setWork_unitId(BaseApplication.user.getInfo().getWork_unitId());
        newStudent.setDepartmentId(BaseApplication.user.getInfo().getDepartmentId());
        newStudent.setUsername(name.getText().toString());
        newStudent.setPhone(phone.getText().toString());

        Retrofit retrofit = RetrofitClient.builderRetrofit();
        GitHubApi repo = retrofit.create(GitHubApi.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Tools.toJson(newStudent));
        Call<StudentBaseData> call = repo.postAddStudent(body);

        call.enqueue(new Callback<StudentBaseData>() {
            @Override
            public void onResponse(Call<StudentBaseData> call,
                                   Response<StudentBaseData> response) {

                switch (response.body().getState()) {
                    case 1:
                        Tools.myMakeText(getApplicationContext(), response.body().getMessage());
                        DataAllCandiates.InfoBean.StudentInfoBean studentInfoBean = new DataAllCandiates.InfoBean.StudentInfoBean();
                        studentInfoBean.setPhone(phone.getText().toString());
                        studentInfoBean.setUsername(name.getText().toString());
                        studentInfoBean.setId(response.body().getStudentId());
                        studentInfoBean.setSelected(true);
                        CandidatesAllAdapter.addDataOne(studentInfoBean);
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
            public void onFailure(Call<StudentBaseData> call, Throwable t) {
                Tools.myMakeText(getApplicationContext(), t.getMessage());
            }
        });

    }

    public class newStudent {

        /**
         * Pack : Case
         * Interface : addStudent
         * work_unitId : 1
         * departmentId : 4
         * username : 小样
         * phone : 15535730421
         */

        private String Pack;
        private String Interface;
        private int work_unitId;
        private int departmentId;
        private String username;
        private String phone;

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

        public int getWork_unitId() {
            return work_unitId;
        }

        public void setWork_unitId(int work_unitId) {
            this.work_unitId = work_unitId;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public class StudentBaseData extends BaseData {
        private int studentId;

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }
    }
}
