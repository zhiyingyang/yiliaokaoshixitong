package com.jsmosce.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.data.DataScoreResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lx on 2017/5/18.
 */
//考试項目
public class ScoreRedultAdapter extends RecyclerView.Adapter<ScoreRedultAdapter.MyViewHolder> implements View.OnClickListener, RecycleItemTouchHelper.ItemTouchHelperCallback {

    /**
     * Created by Administrator on 2016/8/3.
     */
    private LinkedList<DataScoreResult.PointsInfoBean> mDatas = new LinkedList<DataScoreResult.PointsInfoBean>();
    private Context mContext;
    private LayoutInflater inflater;

    public ScoreRedultAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 添加数据
     */
    public void addData(List<DataScoreResult.PointsInfoBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void addDataOne(DataScoreResult.PointsInfoBean data) {
        mDatas.addFirst(data);
        notifyDataSetChanged();
    }

    public LinkedList<DataScoreResult.PointsInfoBean> getDatas() {
        return mDatas;
    }

    /**
     * 刷新数据
     */
    public void upData(List<DataScoreResult.PointsInfoBean> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_score_result_add, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemScoreName.setText(mDatas.get(position).getDescribe());
        holder.itemScoreNumber.setText(mDatas.get(position).getScore());
//        holder.homeParent.setTag(position);
    }

    @Override
    public void onClick(final View v) {
        final int position = (int) v.getTag();

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

        @Bind(R.id.item_score_name)
        TextView itemScoreName;
        @Bind(R.id.item_score_number)
        TextView itemScoreNumber;
        @Bind(R.id.card_view)
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


}