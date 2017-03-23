package com.udacity.porfolioapp.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.DetailsMovieFragment;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.model.MovieContract;
import com.udacity.porfolioapp.model.Trailer;
import com.udacity.porfolioapp.ui.holder.DetailMovieHolder;
import com.udacity.porfolioapp.ui.holder.TrailerViewHolder;

import java.util.List;

/**
 * Created by Juan PC
 */
public class MainMovieRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String BASE_IMAGE="http://image.tmdb.org/t/p/w300/";
    private DetailsMovieFragment detailMovieFragment;
    private DetailsMovieFragment.Callbacks mCallbacks;
    private Context context;
    private List<Object> items;

    private final int DETAIL = 0, TRAILER = 1;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainMovieRecyclerViewAdapter(List<Object> items, Context context, DetailsMovieFragment.Callbacks mCallbacks, DetailsMovieFragment detailMovieFragment) {
        this.items = items;
        this.context=context;
        this.mCallbacks=mCallbacks;
        this.detailMovieFragment=detailMovieFragment;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Movie) {
            return DETAIL;
        } else if (items.get(position) instanceof Trailer) {
            return TRAILER;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        if (viewType==DETAIL){
            View v1 = inflater.inflate(R.layout.item_head_movie, null);
            v1.setTag(items);
            viewHolder = new DetailMovieHolder(v1,mCallbacks);
        }else {
            View v2 = inflater.inflate(R.layout.item_trailer, null);
            v2.setTag(items);
            viewHolder = new TrailerViewHolder(v2,mCallbacks);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType()==DETAIL){
            DetailMovieHolder detailMovieHolder = (DetailMovieHolder) viewHolder;
            fillViewHolderDetail(detailMovieHolder, position);
        }else {
            TrailerViewHolder trailerViewHolder = (TrailerViewHolder) viewHolder;
            fillViewHolderTrailer(trailerViewHolder, position);
        }
    }

    private void fillViewHolderTrailer(TrailerViewHolder trailerViewHolder, int position) {
        Trailer trailer=(Trailer)items.get(position);
        if (trailer!=null){
            trailerViewHolder.getTvTrailer().setText(context.getString(R.string.lb_trailer)+position);
        }
    }

    private void fillViewHolderDetail(DetailMovieHolder detailMovieHolder, int position) {
        Movie movie = (Movie) items.get(position);
        if (movie != null) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                detailMovieHolder.getIvPoster().setTransitionName("profile");
            }
            Glide.with(context).load(BASE_IMAGE+movie.getImageMovie()).placeholder(R.drawable.placeholder).crossFade().into(  detailMovieHolder.getIvPoster());
            detailMovieHolder.getTvPopularityMovie().setText(context.getString(R.string.lb_popularity)+movie.getPopularity());
            detailMovieHolder.getTvRatedMovie().setText(context.getString(R.string.lb_rated_detail)+movie.getVoteAverage());
            detailMovieHolder.getTvVotosMovie().setText(context.getString(R.string.lb_voted)+movie.getVoteCount());
            detailMovieHolder.getTvYearMovie().setText(context.getString(R.string.lb_release)+movie.getYearMovie());
            detailMovieHolder.getTvSummaryMovie().setText(context.getString(R.string.lb_summary)+movie.getDescriptionMovie());
        }

        String selectionArgs[]=new String[]{movie.getNameMovie()};
        Cursor cursor = context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, MovieContract.MovieEntry.COLUMN_TITLE + "=?", selectionArgs, null);

        //QueryBuilder<Movie> movieQueryBuilder=detailMovieFragment.getAppDaoSession().getMovieDao().queryBuilder();

        if (cursor.getCount()==0){
            detailMovieHolder.getIvAddFavo().setChecked(false);
        }else {
            detailMovieHolder.getIvAddFavo().setChecked(true);
        }
       /* if (movieQueryBuilder.where(MovieDao.Properties.Id.eq(movie.getId())).count()!=0){
            detailMovieHolder.getIvAddFavo().setChecked(true);
        }else {
            detailMovieHolder.getIvAddFavo().setChecked(false);
        }*/
    }

}