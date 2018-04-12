package com.oneclass.giphy.ui.state;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by chrisszendrovits on 2018-04-11.
 */

@Database(entities = {FavImageModel.class}, version = 1, exportSchema = false)
public abstract class DataStore extends RoomDatabase {

    private static DataStore instance, inMemInstance;
    private static String databaseName = "schoolmessenger_app.db";

    public abstract FavImageDao favImageDao();

    public static DataStore getInMemoryDatabase(Context context) {
        if (inMemInstance == null) {
            inMemInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), DataStore.class)
                            .allowMainThreadQueries()
                            .build();
        }
        return inMemInstance;
    }

    public static DataStore getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DataStore.class, databaseName)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
        inMemInstance = null;
    }
}