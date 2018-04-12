package com.oneclass.giphy.ui.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.giphy.sdk.core.models.Image;
import com.giphy.sdk.core.models.Media;
import com.oneclass.giphy.ui.adapter.MediaAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chrisszendrovits on 2018-04-11.
 */

public class DataManager {

    private DataStore database;

    public DataManager(DataStore db) {
        this.database = db;
    }

    public boolean isFavImage(Media media) {
        FavImageModel imageModel = null;
        String url = MediaAdapter.getImageUrlFrom(media);

        if (url != null) {
            imageModel = database.favImageDao().selectByUrl(url);
        }
        return imageModel != null;
    }

    public int insertFavImage(Media media) {
        int primaryKey = 0;

        String url = MediaAdapter.getImageUrlFrom(media);

        if (url != null) {
            FavImageModel imageModel = new FavImageModel();
            imageModel.setFavImageUrl(url);

            primaryKey = (int)database.favImageDao().insert(imageModel);
        }
        return primaryKey;
    }

    public void removeFavImage(Media media) {
        String url = MediaAdapter.getImageUrlFrom(media);

        if (url != null) {
            database.favImageDao().deleteByUrl(url);
        }
    }

    public List<FavImageModel> getAllImages() {
        return database.favImageDao().getAllImages();
    }

    public void destroy() {
        if (database != null) {
            database.destroyInstance();
        }
    }
}
