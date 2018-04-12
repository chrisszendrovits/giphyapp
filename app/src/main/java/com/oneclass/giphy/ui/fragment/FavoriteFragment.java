package com.oneclass.giphy.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giphy.sdk.core.models.Media;
import com.oneclass.giphy.R;
import com.oneclass.giphy.ui.activity.GifPreviewActivity;
import com.oneclass.giphy.ui.adapter.MediaAdapter;
import com.oneclass.giphy.ui.state.DataManager;
import com.oneclass.giphy.ui.state.DataStore;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private DataManager dataManager;
    private RecyclerView mRecyclerView;
    private MediaAdapter mAdapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataManager = new DataManager(DataStore.getDatabase(getContext()));
        mAdapter = new MediaAdapter();
        mAdapter.setOnItemClickListener(new MediaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Media media = mAdapter.getMedia(position);
                Intent intent = GifPreviewActivity.createIntent(getContext(), media);
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(MediaAdapter.getViewItemDecoration());
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.refresh(dataManager.getAllMedia());
    }
}
