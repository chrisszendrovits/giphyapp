package com.oneclass.giphy.ui.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.giphy.sdk.core.models.Image;
import com.giphy.sdk.core.models.Media;
import com.oneclass.giphy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nero on 2018-02-22.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private List<Media> mMediaList;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public MediaAdapter() {
        mMediaList = new ArrayList<>();
    }

    public void addAll(List<Media> mediaList) {
        int positionStart = mMediaList.size();
        int itemCount = mediaList.size();

        mMediaList.addAll(mediaList);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    public void refresh(List<Media> mediaList) {
        mMediaList.clear();
        mMediaList.addAll(mediaList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void removeOnItemClickListener() {
        mOnItemClickListener = null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_media, parent, false);

        final ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, viewHolder.itemView);
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getMedia(position));
    }

    @Override
    public int getItemCount() {
        return mMediaList.size();
    }

    public Media getMedia(int position) {
        return mMediaList.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
        }

        void bind(Media media) {
            Image fixedWidthImage = media.getImages().getFixedWidth();

            if (fixedWidthImage != null) {

                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mImageView.getLayoutParams();
                layoutParams.dimensionRatio = String.format("W,%d:%d", fixedWidthImage.getHeight(), fixedWidthImage.getWidth());
                mImageView.requestLayout();

                Glide.with(mImageView)
                        .load(fixedWidthImage.getGifUrl())
                        .into(mImageView);
            }
        }

    }
}
