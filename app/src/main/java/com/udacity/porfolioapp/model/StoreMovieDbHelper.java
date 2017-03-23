package com.udacity.porfolioapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhurtace on 22/03/2017.
 */
public class StoreMovieDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "popmovies.db";
    private static final String TAG =StoreMovieDbHelper.class.getName() ;

    public StoreMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public StoreMovieDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE="CREATE TABLE "+
                Movie.TABLE_NAME+" (" +
                Movie.ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Movie.ROW_TITLE+" TEXT NOT NULL, " +
                Movie.ROW_OVERVIEW+" TEXT NOT NULL, " +
                Movie.ROW_DATE+" TEXT NOT NULL, " +
                Movie.ROW_POSTER+" TEXT NOT NULL, " +
                Movie.ROW_POPULAR+" TEXT NOT NULL," +
                Movie.ROW_BACKDROP+" TEXT NOT NULL," +
                Movie.ROW_VOTE+" INT NOT NULL," +
                Movie.ROW_AVERAGE+"REAL NOT NULL)";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Movie.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public List<Movie> getAllMovies(Cursor cursor) {
        List<Movie> movies = new ArrayList<>();


        try {
            if (cursor.moveToFirst()) {
                do {
                    Movie newMovie = new Movie();
                    newMovie.setId(cursor.getLong(cursor.getColumnIndex(Movie.MovieEntry.COLUMN_ID)));
                    newMovie.setNameMovie(cursor.getString(cursor.getColumnIndex(Movie.MovieEntry.COLUMN_TITLE)));
                    newMovie.setDescriptionMovie(cursor.getString(cursor.getColumnIndex(Movie.MovieEntry.COLUMN_OVERVIEW)));
                    newMovie.setImageMovie(cursor.getString(cursor.getColumnIndex(Movie.MovieEntry.COLUMN_BACKDROP)));
                    newMovie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(Movie.MovieEntry.COLUMN_AVERAGE)));
                    newMovie.setYearMovie(cursor.getString(cursor.getColumnIndex(Movie.MovieEntry.COLUMN_DATE)));
                    newMovie.setPopularity(cursor.getString(cursor.getColumnIndex(Movie.MovieEntry.COLUMN_POPULAR)));
                    newMovie.setUrlMovie(cursor.getString(cursor.getColumnIndex(Movie.MovieEntry.COLUMN_POSTER)));
                    newMovie.setVoteCount(cursor.getInt(cursor.getColumnIndex(Movie.MovieEntry.COLUMN_VOTE)));
                    movies.add(newMovie);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get movies from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return movies;
    }
    public long addMovie(Movie movie) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
        long movieId = -1;
        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).

            ContentValues values = new ContentValues();
            values.put(Movie.MovieEntry.COLUMN_ID, movie.getId());
            values.put(Movie.MovieEntry.COLUMN_TITLE, movie.getNameMovie());
            values.put(Movie.MovieEntry.COLUMN_OVERVIEW, movie.getDescriptionMovie());
            values.put(Movie.MovieEntry.COLUMN_AVERAGE, movie.getVoteAverage());
            values.put(Movie.MovieEntry.COLUMN_POSTER ,movie.getUrlMovie());
            values.put(Movie.MovieEntry.COLUMN_DATE, movie.getYearMovie());
            values.put(Movie.MovieEntry.COLUMN_POPULAR, movie.getPopularity());
            values.put(Movie.MovieEntry.COLUMN_BACKDROP, movie.getImageMovie());
            values.put(Movie.MovieEntry.COLUMN_VOTE, movie.getVoteCount());

            int rows = db.update(Movie.TABLE_NAME, values, Movie.MovieEntry.COLUMN_TITLE + "= ?", new String[]{movie.getNameMovie()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        Movie.MovieEntry.COLUMN_ID, Movie.TABLE_NAME, Movie.MovieEntry.COLUMN_TITLE);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(movie.getNameMovie())});
                try {
                    if (cursor.moveToFirst()) {
                        movieId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
                Log.d(TAG, "update movie");
            } else {
                // user with this userName did not already exist, so insert new user
                // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
                db.insertOrThrow(Movie.TABLE_NAME, null, values);
                db.setTransactionSuccessful();
                Log.d(TAG, "insert movie");
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add movie to database");
        } finally {
            db.endTransaction();
        }
        return movieId;
    }
    public boolean deleteMovie(long idMovie){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(Movie.TABLE_NAME, Movie.MovieEntry.COLUMN_ID + "="+idMovie, null);
            db.setTransactionSuccessful();
            Log.d(TAG, "Delete movie");
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete");
        } finally {
            db.endTransaction();
        }
        return true;
    }

}
