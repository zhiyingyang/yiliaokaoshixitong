package com.jsmosce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsmosce.R;
import com.jsmosce.data.DataSubmitArticle;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lx on 2017/5/18.
 */
//物品項目
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> implements View.OnClickListener, RecycleItemTouchHelper.ItemTouchHelperCallback {

    /**
     * Created by Administrator on 2016/8/3.
     */
    private LinkedList<DataSubmitArticle.GoodsInfoBean> mDatas = new LinkedList<DataSubmitArticle.GoodsInfoBean>();
    private Context mContext;
    private LayoutInflater inflater;

    public ArticleAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public LinkedList<DataSubmitArticle.GoodsInfoBean> getListData() {

        return mDatas;
    }

    /**
     * 添加数据
     */
    public void addData(List<DataSubmitArticle.GoodsInfoBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void addDataOne(DataSubmitArticle.GoodsInfoBean data) {
        mDatas.addFirst(data);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     */
    public void upData(List<DataSubmitArticle.GoodsInfoBean> datas) {
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
        final View view = inflater.inflate(R.layout.item_article, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemArticleName.setText(mDatas.get(position).getName());
        holder.itemArticleSta.setText(mDatas.get(position).getNumber());
        holder.itemArticleId.setText((position + 1)+"");
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

        @Bind(R.id.item_article_name)
        TextView itemArticleName;
        @Bind(R.id.item_article_sta)
        TextView itemArticleSta;
        @Bind(R.id.item_article_id)
        TextView itemArticleId;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


}
