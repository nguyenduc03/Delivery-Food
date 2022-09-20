package com.deliveryfood.Adapter;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager linearLayout;

    public ScrollListener(LinearLayoutManager linearLayout) {
        this.linearLayout = linearLayout;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        int VisibleItemCount = linearLayout.getChildCount();
        int TotalItemCount = linearLayout.getItemCount();
        int FirstVisibleItemPosition = linearLayout.findFirstVisibleItemPosition();

        if (isLoading() || isLastPage()) {
            return;
        }
        if (FirstVisibleItemPosition >= 0 && VisibleItemCount + FirstVisibleItemPosition >= TotalItemCount) {
            LoadMoreItems();
        }
    }

    public abstract void LoadMoreItems();

    public abstract boolean isLoading();

    public abstract boolean isLastPage();
}
