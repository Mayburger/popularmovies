/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package nacoda.android.com.popularmovies.favoritesdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoritesDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "favorDbs.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;


    // Constructor
    FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE " + FavoritesContract.FavoritesEntry.TABLE_NAME + " (" +
                FavoritesContract.FavoritesEntry._ID + " INTEGER PRIMARY KEY, " +
                FavoritesContract.FavoritesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_VOTE_AVERAGE + " INTEGER NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }


    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoritesContract.FavoritesEntry.TABLE_NAME);
        onCreate(db);
    }
}
