package com.jsmosce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jsmosce.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.button_xiangmu)
    Button buttonXiangmu;
    @Bind(R.id.button_jieguo)
    Button buttonJieguo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score2);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        buttonXiangmu.setOnClickListener(this);

        buttonJieguo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.button_xiangmu:
                intent.setClass(ScoreActivity.this, RatingItemActivity.class);
                break;
            case R.id.button_jieguo:
                intent.setClass(ScoreActivity.this, ScoreResultsActivity.class);
                break;
        }
        startActivity(intent);
    }
}
