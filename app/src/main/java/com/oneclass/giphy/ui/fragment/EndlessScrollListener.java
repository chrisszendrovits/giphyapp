package com.oneclass.giphy.ui.fragment;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by chrisszendrovits on 2018-04-10.
 */

class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private boolean isLoading, hasMorePages, isRefreshing;
    private int pageNumber = 0, pastVisibleItems;
    private RefreshList refreshList;

    EndlessScrollListener(RefreshList refreshList) {
        this.isLoading = false;
        this.hasMorePages = true;
        this.refreshList = refreshList;
    }

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();

        int visibleItemCount = manager.getChildCount();
        int totalItemCount = manager.getItemCount();
        int[] firstVisibleItems = manager.findFirstVisibleItemPositions(null);

        if (firstVisibleItems != null && firstVisibleItems.length > 0) {
            pastVisibleItems = firstVisibleItems[0];
        }

        if ((visibleItemCount + pastVisibleItems) >= totalItemCount && !isLoading) {
            isLoading = true;
            if (hasMorePages && !isRefreshing) {
                isRefreshing = true;
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        refreshList.onRefresh(pageNumber);
                    }
                }, 200);
            }
        }
        else {
            isLoading = false;
        }
    }

    public void notifyNoMorePages() {
        this.hasMorePages = false;
    }

    public void notifyMorePages() {
        isRefreshing = false;
        pageNumber = pageNumber + 1;
    }

    public void reset() {
        isRefreshing = false;
        isLoading = false;
        pageNumber = 0;
    }

    interface RefreshList {
        void onRefresh(int pageNumber);
    }
}