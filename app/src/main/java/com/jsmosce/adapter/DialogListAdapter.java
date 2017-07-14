package com.jsmosce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.data.DataHospital;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lx on 2017/5/18.
 */
//注册界面列表
public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.MyViewHolder> implements View.OnClickListener {


    /**
     * Created by Administrator on 2016/8/3.
     */

    private ArrayList<DataHospital.HospitalInfoBean> mDatas = new ArrayList<DataHospital.HospitalInfoBean>();
    private Context mContext;
    private LayoutInflater inflater;
    private DataHospital.HospitalInfoBean HospitalInfoBean = new DataHospital.HospitalInfoBean();

    public DialogListAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 添加数据
     */
    public void addData(List<DataHospital.HospitalInfoBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void changeData(DataHospital.HospitalInfoBean data, int position) {
        mDatas.set(position, data);
        this.notifyItemChanged(position);
    }

    /**
     * 刷新数据
     */
    public void upData(List<DataHospital.HospitalInfoBean> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public ArrayList<DataHospital.HospitalInfoBean> getDatas() {
        return mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_dalog_list, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemDialogTv.setText(mDatas.get(position).getName());
        holder.itemDialogRb.setChecked(mDatas.get(position).isSelectd());

        holder.itemSelectRoomParent.setOnClickListener(this);
        holder.itemSelectRoomParent.setTag(position);
        holder.itemDialogRb.setOnClickListener(this);
        holder.itemDialogRb.setTag(position);
    }

    @Override
    public void onClick(final View v) {
        final int position = (int) v.getTag();
//        switch (v.getId()) {
//            case R.id.item_select_room_parent:
                for (DataHospital.HospitalInfoBean h : mDatas) {
                    h.setSelectd(false);
                }
                mDatas.get(position).setSelectd(true);
                notifyDataSetChanged();

                HospitalInfoBean = mDatas.get(position);
      //          break;
    //    }
    }

    //获取当前选中的数据
    public DataHospital.HospitalInfoBean getHospitalInfoBean() {
        return HospitalInfoBean;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_dialog_tv)
        TextView itemDialogTv;
        @Bind(R.id.item_dialog_rb)
        RadioButton itemDialogRb;
        @Bind(R.id.item_select_room_parent)
        LinearLayout itemSelectRoomParent;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
