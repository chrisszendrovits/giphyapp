package com.oneclass.giphy.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giphy.sdk.core.models.enums.MediaType;
import com.giphy.sdk.core.network.api.CompletionHandler;
import com.giphy.sdk.core.network.api.GPHApiClient;
import com.giphy.sdk.core.network.response.ListMediaResponse;
import com.oneclass.giphy.R;
import com.oneclass.giphy.ui.activity.GifPreviewActivity;
import com.oneclass.giphy.ui.adapter.MediaAdapter;

public class TrendingFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private GPHApiClient mClient;
    private MediaAdapter mAdapter;
    private StaggeredGridLayoutManager layoutManager;

    private int visibleThreshold = 7, viewLimit = 25;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading = false;

    public TrendingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mClient = new GPHApiClient(context.getString(R.string.giphy_api_key));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mAdapter = new MediaAdapter();
        mAdapter.setOnItemClickListener(new MediaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                // TODO: Pass the media object to show in the GifPreviewActivity

            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();

                int spanIndex = layoutParams.getSpanIndex();
                int index = parent.getChildLayoutPosition(view);
                int itemCount = parent.getAdapter().getItemCount();

                switch (spanIndex) {
                    case 0:
                        outRect.left = 16;
                        outRect.right = 8;
                        break;
                    case 1:
                        outRect.left = 8;
                        outRect.right = 16;
                        break;
                }

                if (index < 2) {
                    outRect.top = 16;
                    outRect.bottom = 8;
                } else if (index == itemCount - 1) {
                    outRect.top = 8;
                    outRect.bottom = 16;
                } else if (index == itemCount - 2 && layoutParams.isFullSpan()) {
                    outRect.top = 8;
                    outRect.bottom = 16;
                } else {
                    outRect.top = 8;
                    outRect.bottom = 8;
                }
            }
        });

        EndlessScrollListener scrollListener = new EndlessScrollListener(new EndlessScrollListener.RefreshList() {
            @Override
            public void onRefresh(int pageNumber) {
                loadTrendingImages(pageNumber * viewLimit);
            }
        });

        mRecyclerView.addOnScrollListener(scrollListener);
        loadTrendingImages(0);

        // TODO: Configure the recycler view to load more media when reaching bottom

    }

    private void loadTrendingImages(int offset) {
        mClient.trending(MediaType.gif, viewLimit, offset, null, new CompletionHandler<ListMediaResponse>() {
            @Override
            public void onComplete(ListMediaResponse listMediaResponse, Throwable throwable) {
                if (listMediaResponse != null) {
                    mAdapter.addAll(listMediaResponse.getData());
                } else if (throwable != null) {
                    Toast.makeText(getActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }
}
