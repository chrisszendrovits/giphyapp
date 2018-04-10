package com.oneclass.giphy.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oneclass.giphy.R;
import com.oneclass.giphy.ui.fragment.FavoriteFragment;
import com.oneclass.giphy.ui.fragment.TrendingFragment;

/**
 * Created by nero on 2018-02-22.
 */

public final class MainPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public MainPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TrendingFragment();
            case 1:
                return new FavoriteFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_trending);
            case 1:
                return mContext.getString(R.string.tab_favorite);
            default:
                return null;
        }
    }
}
