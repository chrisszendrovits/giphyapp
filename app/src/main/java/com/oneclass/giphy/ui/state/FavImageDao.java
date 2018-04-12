package com.oneclass.giphy.ui.state;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by chrisszendrovits on 2018-04-11.
 */

@Dao
public interface FavImageDao {

    @Query("Select * FROM favouriteImages WHERE favImageUrl = :url")
    FavImageModel selectByUrl(String url);

    @Query("SELECT * FROM favouriteImages")
    List<FavImageModel> getAllImages();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FavImageModel imageModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<FavImageModel> imageModels);

    @Query("DELETE FROM favouriteImages WHERE favImageUrl = :url")
    void deleteByUrl(String url);

    @Delete
    void delete(FavImageModel imageModel);
}