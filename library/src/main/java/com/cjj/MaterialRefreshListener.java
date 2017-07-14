package com.cjj;

public interface MaterialRefreshListener {
    public void onfinish();

    public void onRefresh(MaterialRefreshLayout materialRefreshLayout);

    public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout);
}
