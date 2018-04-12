package com.oneclass.giphy.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.oneclass.giphy.R;
import com.oneclass.giphy.ui.adapter.MainPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public interface SearchQueryListener {
        void updateSearchQuery(String query);
    }

    private List<SearchQueryListener> listeners = new ArrayList<>();
    private ViewPager mViewPager;
    private Menu menu;

    public void addListener(SearchQueryListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(SearchQueryListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup ViewPager with TabLayout
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        MainPagerAdapter adapter = new MainPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MenuItem menuItem = menu.findItem(R.id.action_search);
                SearchView searchView = (SearchView) menuItem.getActionView();

                if (position == 0) {
                    searchView.setVisibility(View.VISIBLE);
                }
                else {
                    searchView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, this.menu = menu);
        final SearchView searchView = getSearchView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                for (SearchQueryListener listener : listeners) {
                    listener.updateSearchQuery(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    for (SearchQueryListener listener : listeners) {
                        listener.updateSearchQuery("");
                    }
                }
                return true;
            }
        });

        return true;
    }

    protected SearchView getSearchView() {
        MenuItem menuItem = menu.findItem(R.id.action_search);
        return (SearchView) menuItem.getActionView();
    }
}
