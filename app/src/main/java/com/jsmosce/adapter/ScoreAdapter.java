package com.jsmosce.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.activity.ExamDetailsActivity;
import com.jsmosce.data.DataCaseDesign;
import com.jsmosce.view.percentsuppor.PercentLinearLayout;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lx on 2017/5/18.
 */
//考試項目
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.MyViewHolder> implements View.OnClickListener, RecycleItemTouchHelper.ItemTouchHelperCallback {


    /**
     * Created by Administrator on 2016/8/3.
     */
    private LinkedList<DataCaseDesign.DesignInfoBean> mDatas = new LinkedList<DataCaseDesign.DesignInfoBean>();
    private Context mContext;
    private LayoutInflater inflater;

    public ScoreAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 添加数据
     */
    public void addData(List<DataCaseDesign.DesignInfoBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDataOne(DataCaseDesign.DesignInfoBean datas) {
        mDatas.addLast(datas);
        notifyDataSetChanged();
    }


    public void changeData(DataCaseDesign.DesignInfoBean data, int position) {
        mDatas.set(position, data);
        this.notifyItemChanged(position);
    }

    /**
     * 刷新数据
     */
    public void upData(List<DataCaseDesign.DesignInfoBean> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public LinkedList<DataCaseDesign.DesignInfoBean> getDatas() {
        return mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_score_item, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemScoreItem.setText(mDatas.get(position).getName());
        holder.itemScoreEv.setText(mDatas.get(position).getName2());
        holder.itemSelectRoomParent.setOnClickListener(this);
        holder.itemSelectRoomParent.setTag(position);
        mDatas.get(position).setOrderby(position);

    }

    @Override
    public void onClick(final View v) {
        final int position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.item_select_room_parent:
                Intent intent = new Intent(mContext, ExamDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putSerializable("params", mDatas.get(position));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                // ((ScoreActivity) mContext).startActivityForResult(intent, 0);
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
        @Bind(R.id.item_select_room_parent)
        PercentLinearLayout itemSelectRoomParent;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
