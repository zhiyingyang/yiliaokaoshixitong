package com.jsmosce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.data.DataAllCandiates;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lx on 2017/5/18.
 */
//所有考生Adapter
public class CandidatesManagementAdapter extends RecyclerView.Adapter<CandidatesManagementAdapter.MyViewHolder> implements View.OnClickListener, RecycleItemTouchHelper.ItemTouchHelperCallback {


    /**
     * Created by Administrator on 2016/8/3.
     */
    //全部数据
    private LinkedList<DataAllCandiates.InfoBean.StudentInfoBean> mDatas = new LinkedList<DataAllCandiates.InfoBean.StudentInfoBean>();
    private Context mContext;
    private LayoutInflater inflater;

    public CandidatesManagementAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 添加数据
     */
    public void addData(List<DataAllCandiates.InfoBean.StudentInfoBean> datas) {
        if (datas == null) return;
//        for (DataAllCandiates.InfoBean.StudentInfoBean newdata : datas) {
//            for (DataAllCandiates.InfoBean.StudentInfoBean olddata : mDatas) {
//                if (newdata.getId() == olddata.getId()) {
//                    datas.remove(newdata);
//                }
//            }
//        }
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDataOne(DataAllCandiates.InfoBean.StudentInfoBean datas) {
        mDatas.addLast(datas);
        notifyDataSetChanged();
    }


    public void changeData(DataAllCandiates.InfoBean.StudentInfoBean data, int position) {
        mDatas.set(position, data);
        this.notifyItemChanged(position);
    }

    /**
     * 刷新数据
     */
    public void upData(List<DataAllCandiates.InfoBean.StudentInfoBean> datas) {
        if (datas == null) return;
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    //返回选择的数据
    public LinkedList<DataAllCandiates.InfoBean.StudentInfoBean> getDatas() {
        return mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_candidtes_management, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemScoreItem.setText(mDatas.get(position).getUsername());
        holder.itemScoreEv.setText(mDatas.get(position).getPhone());
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
        @Bind(R.id.item_score_item)
        TextView itemScoreItem;
        @Bind(R.id.item_score_ev)
        TextView itemScoreEv;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
