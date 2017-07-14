package com.jsmosce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.Tools.FavorEvent;
import com.jsmosce.data.DataSelectRoom;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lx on 2017/5/18.
 */

public class SelectRoomItemAdapter extends RecyclerView.Adapter<SelectRoomItemAdapter.MyViewHolder> implements View.OnClickListener {

    /**
     * Created by Administrator on 2016/8/3.
     */
    private List<Object> mDatas = new ArrayList<Object>();
    private Context mContext;
    private LayoutInflater inflater;

    public SelectRoomItemAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 添加数据
     */
    public void addData(Object datas) {
        mDatas.addAll((Collection<?>) datas);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     */
    public void upData(Object datas) {
        mDatas.clear();
        mDatas.addAll((Collection<?>) datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_select_room_item, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (mDatas.get(position) instanceof DataSelectRoom.InfoBean.PmBean) {
            holder.itemSelectRoomItem.setText(((DataSelectRoom.InfoBean.PmBean) mDatas.get(position)).getName());
        } else {
            holder.itemSelectRoomItem.setText(((DataSelectRoom.InfoBean.AmBean) mDatas.get(position)).getName());
        }
        holder.itemSelectRoom.setTag(position);
        holder.itemSelectRoom.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        final int position = (int) v.getTag();
        FavorEvent anyEventType = new FavorEvent();
        anyEventType.setId(FavorEvent.Select_Room);
        anyEventType.setObject(mDatas.get(position));
        EventBus.getDefault().postSticky(anyEventType);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_select_room_item)
        TextView itemSelectRoomItem;
        @Bind(R.id.item_select_room_parent)
        View itemSelectRoom;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }

}
