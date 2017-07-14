package com.jsmosce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jsmosce.R;
import com.jsmosce.Tools.AndroidBug5497Workaround;
import com.jsmosce.Tools.FavorEvent;
import com.jsmosce.Tools.Tools;
import com.jsmosce.base.BaseActivity;
import com.jsmosce.data.DataCaseDesign;
import com.jsmosce.view.TitleView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

//考试项目简介 详情
public class ExamDetailsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.exam_title)
    TitleView examTitle;
//    @Bind(R.id.exam_kaoanbianhao)
//    TextView examKaoanbianhao;
//    @Bind(R.id.exam_bianzhuangdanwei)
//    TextView examBianzhuangdanwei;
//    @Bind(R.id.exam_ceyanhexing)
//    TextView examCeyanhexing;
//    @Bind(R.id.exam_bianzhuanzhe)
//    TextView examBianzhuanzhe;
//    @Bind(R.id.exam_kaoanmingchen)
//    TextView examKaoanmingchen;
//    @Bind(R.id.exam_ceyanmingchen)
//    TextView examCeyanmingchen;
    @Bind(R.id.exam_main_title)
    EditText examMainTitle;
    @Bind(R.id.subtitle)
    EditText subtitle;
    @Bind(R.id.exam_detailed_tv)
    EditText examDetailedTv;
    @Bind(R.id.exam_true)
    Button examTrue;

    DataCaseDesign.DesignInfoBean designInfoBean;

    private int flag = FavorEvent.Case_Detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_details);
        AndroidBug5497Workaround.assistActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        examTrue.setOnClickListener(this);
        if (bundle == null) {

            flag = FavorEvent.Case_add;
            designInfoBean = new DataCaseDesign.DesignInfoBean();
            return;

        } else {
            designInfoBean = (DataCaseDesign.DesignInfoBean) bundle.getSerializable("params");
            examMainTitle.setText(designInfoBean.getName());
            examTitle.setTitle(designInfoBean.getName());
            subtitle.setText(designInfoBean.getName2());
            examDetailedTv.setText(designInfoBean.getMessage());
        }






    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exam_true:
                if ("".equals(examMainTitle.getText().toString())) {
                    Tools.myMakeText(getApplicationContext(),"主标题不能为空");
                    return;
                }


                FavorEvent anyEventType = new FavorEvent();
                anyEventType.setId(flag);
                designInfoBean.setName(examMainTitle.getText().toString());
                designInfoBean.setName2(subtitle.getText().toString());
                designInfoBean.setMessage(examDetailedTv.getText().toString());

                anyEventType.setObject(designInfoBean);
                EventBus.getDefault().postSticky(anyEventType);
                this.finish();
                break;
        }
    }
}
