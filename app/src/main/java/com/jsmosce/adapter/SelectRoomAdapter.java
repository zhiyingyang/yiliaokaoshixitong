package com.jsmosce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.data.DataSelectRoom;
import com.jsmosce.view.percentsuppor.PercentLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lx on 2017/5/18.
 */

public class SelectRoomAdapter extends RecyclerView.Adapter<SelectRoomAdapter.MyViewHolder> implements View.OnClickListener {


    /**
     * Created by Administrator on 2016/8/3.
     */
    private List<DataSelectRoom.InfoBean> mDatas = new ArrayList<DataSelectRoom.InfoBean>();
    private Context mContext;
    private LayoutInflater inflater;

    public SelectRoomAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 添加数据
     */
    public void addData(List<DataSelectRoom.InfoBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     */
    public void upData(List<DataSelectRoom.InfoBean> datas) {
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
        final View view = inflater.inflate(R.layout.item_select_room, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       // holder.homeParent.setTag(position);

//        holder.itemHomeSta.setText(string);
//
//        holder.itemHomeName.setText(mDatas.get(position).getName());
//        holder.homeParent.setOnClickListener(this);

    }

    @Override
    public void onClick(final View v) {
        final int position = (int) v.getTag();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_select_room_t)
        TextView itemSelectRoomT;
        @Bind(R.id.tem_select_room_rv)
        RecyclerView temSelectRoomRv;
        @Bind(R.id.item_select_room)
        PercentLinearLayout itemSelectRoom;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


}
