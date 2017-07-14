package com.jsmosce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.data.DataScoreing;
import com.jsmosce.data.DataSubmitScoreing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lx on 2017/5/18.
 */
//考试項目
public class ScoreingAdapter extends RecyclerView.Adapter<ScoreingAdapter.MyViewHolder> implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    /**
     * Created by Administrator on 2016/8/3.
     */
    private LinkedList<DataScoreing.InfoBean.CateBean> mDatas = new LinkedList<DataScoreing.InfoBean.CateBean>();
    private List<DataScoreing.InfoBean.PointsBean> pointsBeen = new LinkedList<DataScoreing.InfoBean.PointsBean>();
    private Context mContext;
    private LayoutInflater inflater;
    String[] mItems;
    ArrayAdapter<String> adapter;
    private List<DataSubmitScoreing.ScoreInfoBean> dataSubmitScoreing = new ArrayList<DataSubmitScoreing.ScoreInfoBean>();


    public ScoreingAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);

    }

    /**
     * 添加数据
     */
    public void addData(List<DataScoreing.InfoBean.CateBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void addDataOne(DataScoreing.InfoBean.CateBean data) {
        mDatas.addFirst(data);
        notifyDataSetChanged();
    }

    public LinkedList<DataScoreing.InfoBean.CateBean> getDatas() {
        return mDatas;
    }

    /**
     * 刷新数据
     */
    public void upData(List<DataScoreing.InfoBean.CateBean> datas, List<DataScoreing.InfoBean.PointsBean> pointsBeen) {
        mDatas.clear();
        mDatas.addAll(datas);
        this.pointsBeen.clear();
        this.pointsBeen.addAll(pointsBeen);

        mItems = new String[pointsBeen.size()+1];
        mItems[0] = "请选择评分";

        for (int i = 1; i < mItems.length; i++) {
            mItems[i] = pointsBeen.get(i - 1).getDescribe();
        }
        adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item, mItems);

        for (int i = 0; i < mDatas.size(); i++) {
            DataSubmitScoreing.ScoreInfoBean scoreInfoBean = new DataSubmitScoreing.ScoreInfoBean();
            scoreInfoBean.setCase_assessmentId(mDatas.get(i).getId());
            scoreInfoBean.setResult("null");
            dataSubmitScoreing.add(scoreInfoBean);
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_scoreing, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
          holder.itemScoreTitle.setText(mDatas.get(position).getDescribe());
        holder.itemScoreSpinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.itemScoreSpinner.setOnItemSelectedListener(this);
        holder.itemScoreSpinner.setTag(position);

    }

    @Override
    public void onClick(final View v) {
        final int position = (int) v.getTag();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            //Tools.myMakeText(mContext, "请选择正确的评分");
            return;
        }

        dataSubmitScoreing.get((Integer) parent.getTag()).setResult(this.pointsBeen.get(position - 1).getScore());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public List<DataSubmitScoreing.ScoreInfoBean> getDataSubmitScoreing() {
        return dataSubmitScoreing;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_score_spinner)
        Spinner itemScoreSpinner;
        @Bind(R.id.item_score_title)
        TextView itemScoreTitle;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
