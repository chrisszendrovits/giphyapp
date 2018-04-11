package com.oneclass.giphy.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giphy.sdk.core.models.Media;
import com.giphy.sdk.core.models.enums.MediaType;
import com.giphy.sdk.core.network.api.CompletionHandler;
import com.giphy.sdk.core.network.api.GPHApiClient;
import com.giphy.sdk.core.network.response.ListMediaResponse;
import com.oneclass.giphy.R;
import com.oneclass.giphy.ui.activity.GifPreviewActivity;
import com.oneclass.giphy.ui.activity.MainActivity;
import com.oneclass.giphy.ui.adapter.MediaAdapter;

import java.util.List;

public class TrendingFragment extends Fragment implements MainActivity.SearchQueryListener {

    private RecyclerView mRecyclerView;
    private GPHApiClient mClient;
    private MediaAdapter mAdapter;
    private StaggeredGridLayoutManager layoutManager;
    private EndlessScrollListener scrollListener;

    private String searchQuery;
    private int viewLimit = 25;
    private boolean showTrending = true, reset = false;

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
                Media media = mAdapter.getMedia(position);
                Intent intent = GifPreviewActivity.createIntent(getContext(), media);
                startActivity(intent);
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

        scrollListener = new EndlessScrollListener(new EndlessScrollListener.RefreshList() {
            @Override
            public void onRefresh(int pageNumber) {
                loadImages(searchQuery, pageNumber * viewLimit);
            }
        });

        mRecyclerView.addOnScrollListener(scrollListener);
        loadImages(searchQuery, 0);
    }

    private void loadImages(String searchQuery, int offset) {
        if (searchQuery != null && !searchQuery.isEmpty()) {
            if (showTrending) {
                scrollListener.reset();
                reset = true;
                showTrending = false;
            }
            mClient.search(searchQuery, MediaType.gif, viewLimit, offset, null, null, completionHandler);
        }
        else {
            if (!showTrending) {
                scrollListener.reset();
                reset = true;
                showTrending = true;
            }
            mClient.trending(MediaType.gif, viewLimit, offset, null, completionHandler);
        }
    }

    CompletionHandler<ListMediaResponse> completionHandler = new CompletionHandler<ListMediaResponse>() {
        @Override
        public void onComplete(ListMediaResponse listMediaResponse, Throwable throwable) {
            boolean hasMoreData = false;

            if (throwable != null) {
                Toast.makeText(getActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG)
                        .show();
            }
            else if (listMediaResponse != null) {
                List<Media> mediaList = listMediaResponse.getData();

                if (mediaList != null && mediaList.size() > 0) {
                    if (reset) {
                        mAdapter.refresh(listMediaResponse.getData());
                    }
                    else {
                        mAdapter.addAll(listMediaResponse.getData());
                    }
                    hasMoreData = true;
                }
            }

            if (hasMoreData) {
                scrollListener.notifyMorePages();
            }
            else {
                scrollListener.notifyNoMorePages();
            }
            reset = false;
        }
    };

    @Override
    public void updateSearchQuery(String query) {
        loadImages(this.searchQuery = query, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).addListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).removeListener(this);
    }
}