package com.oneclass.giphy.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.giphy.sdk.core.models.Media;
import com.oneclass.giphy.R;

public class GifPreviewActivity extends AppCompatActivity {

    ImageView mImageView;

    Button mShareButton;

    private Media mMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_preview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mImageView = (ImageView) findViewById(R.id.image_view);

        mShareButton = (Button) findViewById(R.id.share_button);

        // TODO: Get the Media object for display

        // TODO: Show the original Gif in `mImageView`

        // TODO: Implement sharing
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gif_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_favorite:
                toggleFavorite();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleFavorite() {
        // TODO: Toggle and persist the favorite state of the Media object

        boolean isFavorite = false;

        Toast.makeText(this,
                isFavorite ? "Marked as favorite" : "Removed from favorite",
                Toast.LENGTH_LONG).show();
    }
}
