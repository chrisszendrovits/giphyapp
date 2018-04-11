package com.oneclass.giphy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.giphy.sdk.core.models.Image;
import com.giphy.sdk.core.models.Media;
import com.oneclass.giphy.R;

public class GifPreviewActivity extends AppCompatActivity {

    static public final String mediaExtra = "media_extra";

    ImageView mImageView;
    Button mShareButton;
    private Media mMedia;

    public static Intent createIntent(Context context, Media media) {
        Intent previewIntent = new Intent(context, GifPreviewActivity.class);
        previewIntent.putExtra(GifPreviewActivity.mediaExtra, media);
        return previewIntent;
    }

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

        Intent intent = getIntent();

        if (intent != null) {
            mMedia = intent.getParcelableExtra(GifPreviewActivity.mediaExtra);
        }

        if (mMedia != null) {
            setImage(mMedia);
        }

        // TODO: Implement sharing
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    protected void setImage(Media media) {
        Image fixedWidthImage = media.getImages().getFixedWidth();

        if (fixedWidthImage != null) {

            Glide.with(mImageView)
                    .load(fixedWidthImage.getGifUrl())
                    .into(mImageView);
        }
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