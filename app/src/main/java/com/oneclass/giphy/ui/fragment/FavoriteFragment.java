package com.oneclass.giphy.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giphy.sdk.core.models.Media;
import com.oneclass.giphy.R;
import com.oneclass.giphy.ui.adapter.MediaAdapter;
import com.oneclass.giphy.ui.state.DataManager;
import com.oneclass.giphy.ui.state.DataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private DataManager dataManager;

    private RecyclerView mRecyclerView;
    private MediaAdapter mAdapter;
    private List<Media> mediaList = new ArrayList<>();

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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        dataManager = new DataManager(DataStore.getDatabase(getContext()));
        dataManager.getAllImages();

        mAdapter = new MediaAdapter();
        mAdapter.addAll(mediaList);
    }
}
