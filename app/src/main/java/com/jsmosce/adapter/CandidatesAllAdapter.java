package com.jsmosce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.data.DataAllCandiates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lx on 2017/5/18.
 */
//所有考生Adapter
public class CandidatesAllAdapter extends RecyclerView.Adapter<CandidatesAllAdapter.MyViewHolder> implements View.OnClickListener, RecycleItemTouchHelper.ItemTouchHelperCallback {

    /**
     * Created by Administrator on 2016/8/3.
     */
    //全部数据
    private LinkedList<DataAllCandiates.InfoBean.StudentInfoBean> mDatas = new LinkedList<DataAllCandiates.InfoBean.StudentInfoBean>();
    //筛选后的数据
    private LinkedList<DataAllCandiates.InfoBean.StudentInfoBean> mfilterDatas = new LinkedList<DataAllCandiates.InfoBean.StudentInfoBean>();
    //将要提交的数据
    private Map<Integer, DataAllCandiates.InfoBean.StudentInfoBean> mSubmits = new HashMap<Integer, DataAllCandiates.InfoBean.StudentInfoBean>();
    private Context mContext;
    private LayoutInflater inflater;

    public CandidatesAllAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 筛选逻辑
     *
     * @param query
     * @return
     */
    public void filter(String query) {
        if (TextUtils.isEmpty(query)) {
            upSearchData(mDatas);
            return;
        }

        query = query.toLowerCase();
        final List<DataAllCandiates.InfoBean.StudentInfoBean> filteredModelList = new ArrayList<>();
        for (DataAllCandiates.InfoBean.StudentInfoBean people : mDatas) {

            final String name = people.getUsername().toLowerCase();
            final String phone = people.getPhone().toLowerCase();

            if (name.contains(query) || phone.contains(query)) {
                filteredModelList.add(people);
            }
        }

        upSearchData(filteredModelList);
    }


    /**
     * 添加数据
     */
    public void addData(List<DataAllCandiates.InfoBean.StudentInfoBean> datas) {
        if (datas == null) return;
        mDatas.addAll(datas);
        mfilterDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDataOne(DataAllCandiates.InfoBean.StudentInfoBean datas) {
        mDatas.addFirst(datas);
        mfilterDatas.addFirst(datas);
        mSubmits.put(datas.getId(), datas);
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
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     */
    public void upSearchData(List<DataAllCandiates.InfoBean.StudentInfoBean> datas) {
        mfilterDatas.clear();
        mfilterDatas.addAll(datas);
        notifyDataSetChanged();
    }

    //返回选择的数据
    public List<DataAllCandiates.InfoBean.StudentInfoBean> getDatas() {
        // 将Map Key 转化为List
        List<DataAllCandiates.InfoBean.StudentInfoBean> mapValuesList = new ArrayList<DataAllCandiates.InfoBean.StudentInfoBean>(mSubmits.values());
        return mapValuesList;
    }

    @Override
    public int getItemCount() {
        return mfilterDatas.size();
    }


    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_candidtes_all, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemScoreItem.setText(mfilterDatas.get(position).getUsername());
        holder.itemScoreEv.setText(mfilterDatas.get(position).getPhone());
        if (mfilterDatas.get(position).isSelected()) {
            holder.candidatesAdd.setBackgroundResource(R.drawable.icon_less);
        } else {
            holder.candidatesAdd.setBackgroundResource(R.drawable.icon_add);
        }
        holder.candidatesAdd.setTag(position);
    }

    //选择取消选择
    @Override
    public void onClick(final View v) {
        final int position = (int) v.getTag();

        if (mSubmits.get(mfilterDatas.get(position).getId()) == null) {
            mSubmits.put(mfilterDatas.get(position).getId(), mfilterDatas.get(position));
            // mDatas.get(position).setSelected(true);
            mfilterDatas.get(position).setSelected(true);
            v.setBackgroundResource(R.drawable.icon_less);
        } else {
            v.setBackgroundResource(R.drawable.icon_add);
            mSubmits.remove(mfilterDatas.get(position).getId());
            //mDatas.get(position).setSelected(false);
            mfilterDatas.get(position).setSelected(false);
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
        @Bind(R.id.candidates_add)
        View candidatesAdd;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            candidatesAdd.setOnClickListener(CandidatesAllAdapter.this);
        }
    }

}
