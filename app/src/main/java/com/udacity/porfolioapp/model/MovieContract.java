package com.udacity.porfolioapp.model;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jhurtace on 23/03/2017.
 */
public class MovieContract {


    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.udacity.porfolioapp";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        // Task table and column names
        public static final String TABLE_NAME = "movies";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_POSTER="POSTER";
        public static final String COLUMN_TITLE="TITLE";
        public static final String COLUMN_DATE="DATE";
        public static final String COLUMN_POPULAR="POPULAR";
        public static final String COLUMN_BACKDROP="BACKDROP";
        public static final String COLUMN_OVERVIEW="OVERVIEW";
        public static final String COLUMN_VOTE="VOTE";
        public static final String COLUMN_AVERAGE="AVERAGE";

    }

}
