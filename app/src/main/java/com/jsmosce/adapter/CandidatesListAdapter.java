package com.jsmosce.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.activity.ScoreingActivity;
import com.jsmosce.data.DataStudentsList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lx on 2017/5/18.
 */
//考試項目
public class CandidatesListAdapter extends RecyclerView.Adapter<CandidatesListAdapter.MyViewHolder> implements View.OnClickListener, RecycleItemTouchHelper.ItemTouchHelperCallback {


    /**
     * Created by Administrator on 2016/8/3.
     */
    private ArrayList<DataStudentsList.InfoBean.UserInfoBean> mDatas = new ArrayList<DataStudentsList.InfoBean.UserInfoBean>();
    private Context mContext;
    private LayoutInflater inflater;
    private DataStudentsList.InfoBean.CaseInfoBean caseInfoBean;

    public CandidatesListAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);

    }

    /**
     * 添加数据
     */
    public void addData(List<DataStudentsList.InfoBean.UserInfoBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDataOne(DataStudentsList.InfoBean.UserInfoBean datas) {
        //mDatas.addLast(datas);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }


    public void changeData(DataStudentsList.InfoBean.UserInfoBean data, int position) {
        mDatas.set(position, data);
        this.notifyItemChanged(position);
    }

    /**
     * 刷新数据
     */
    public void upData(List<DataStudentsList.InfoBean.UserInfoBean> datas, DataStudentsList.InfoBean.CaseInfoBean caseInfoBean) {
        mDatas.clear();
        mDatas.addAll(datas);
        this.caseInfoBean = caseInfoBean;
        notifyDataSetChanged();
    }

    public ArrayList<DataStudentsList.InfoBean.UserInfoBean> getDatas() {
        return mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = inflater.inflate(R.layout.item_candidates, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemScoreItem.setText(mDatas.get(position).getUsername());
        holder.itemScoreEv.setText(mDatas.get(position).getPhone());
        holder.cardView.setOnClickListener(this);
        holder.cardView.setTag(position);

    }

    @Override
    public void onClick(final View v) {
        final int position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.card_view:
                Intent intent = new Intent(mContext, ScoreingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();

                bundle.putSerializable("params", caseInfoBean);
                bundle.putSerializable("UserInfoBean", mDatas.get(position));
                intent.putExtra("position", position);

                intent.putExtras(bundle);
                mContext.startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemDelete(int positon) {
        mDatas.remove(positon);
        notifyItemRemoved(positon);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        Collections.swap(mDatas, fromPosition, toPosition);//交换数据
        notifyItemMoved(fromPosition, toPosition);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_score_item)
        TextView itemScoreItem;
        @Bind(R.id.item_score_ev)
        TextView itemScoreEv;
        @Bind(R.id.card_view)
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
