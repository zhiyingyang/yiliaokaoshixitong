package com.jsmosce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.data.DataSubmitScoreKind;
import com.jsmosce.view.percentsuppor.PercentRelativeLayout;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lx on 2017/5/18.
 */
//考试項目
public class ScoreItemAdapter extends RecyclerView.Adapter<ScoreItemAdapter.MyViewHolder> implements View.OnClickListener,RecycleItemTouchHelper.ItemTouchHelperCallback{

    /**
     * Created by Administrator on 2016/8/3.
     */
    private LinkedList<DataSubmitScoreKind.CateInfoBean> mDatas = new LinkedList<DataSubmitScoreKind.CateInfoBean>();
    private Context mContext;
    private LayoutInflater inflater;

    public ScoreItemAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 添加数据
     */
    public void addData(List<DataSubmitScoreKind.CateInfoBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void addDataOne(DataSubmitScoreKind.CateInfoBean data) {
        mDatas.addFirst(data);
        notifyDataSetChanged();
    }
    public LinkedList<DataSubmitScoreKind.CateInfoBean> getDatas(){
        return mDatas;
    }

    /**
     * 刷新数据
     */
    public void upData(List<DataSubmitScoreKind.CateInfoBean> datas) {
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
        final View view = inflater.inflate(R.layout.item_scoring_rules, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemScoreName.setText(mDatas.get(position).getDescribe());
//        holder.homeParent.setOnClickListener(this);
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
        Collections.swap(mDatas,fromPosition,toPosition);//交换数据
        notifyItemMoved(fromPosition,toPosition);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_score_name)
        TextView itemScoreName;
        @Bind(R.id.home_parent)
        PercentRelativeLayout homeParent;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


}
