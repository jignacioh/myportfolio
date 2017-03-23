package com.udacity.porfolioapp.model.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.model.StoreMovieDbHelper;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by jhurtace on 22/03/2017.
 */
public class MovieContentProvider extends ContentProvider {

    // database
    private StoreMovieDbHelper database;

    // used for the UriMacher
    private static final int MOVIES = 10;
    private static final int MOVIE_ID = 20;

    private static final String AUTHORITY = "com.udacity.porfolioapp.model.provider";

    private static final String BASE_PATH = "storemovie";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/movies";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/movie";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, MOVIES);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        database = new StoreMovieDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(Movie.TABLE_NAME);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case MOVIES:
                break;
            case MOVIE_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(Movie.MovieEntry.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case MOVIES:
                id = sqlDB.insert(Movie.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case MOVIES:
                rowsDeleted = sqlDB.delete(Movie.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case MOVIE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(
                            Movie.TABLE_NAME,
                            Movie.MovieEntry.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(
                            Movie.TABLE_NAME,
                            Movie.MovieEntry.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case MOVIES:
                rowsUpdated = sqlDB.update(Movie.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case MOVIE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(Movie.TABLE_NAME,
                            values,
                            Movie.MovieEntry.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(Movie.TABLE_NAME,
                            values,
                            Movie.MovieEntry.COLUMN_ID  + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = {
                Movie.MovieEntry.COLUMN_AVERAGE,
                Movie.MovieEntry.COLUMN_BACKDROP,
                Movie.MovieEntry.COLUMN_DATE,
                Movie.MovieEntry.COLUMN_OVERVIEW,
                Movie.MovieEntry.COLUMN_POPULAR,
                Movie.MovieEntry.COLUMN_POSTER,
                Movie.MovieEntry.COLUMN_TITLE,
                Movie.MovieEntry.COLUMN_VOTE,
                Movie.MovieEntry.COLUMN_ID };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }

}