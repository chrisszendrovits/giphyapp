package com.oneclass.giphy.ui.state;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by chrisszendrovits on 2018-04-10.
 */

@Entity(tableName = "favouriteImages")
public class FavImageModel {

    @PrimaryKey(autoGenerate = true)
    private int favImageId;

    @ColumnInfo(name = "favImageUrl")
    private String favImageUrl;

    public int getFavImageId() { return this.favImageId; }
    public void setFavImageId(int id) { this.favImageId = id; }

    public String getFavImageUrl() { return this.favImageUrl; }
    public void setFavImageUrl(String url) { this.favImageUrl = url; }
}
