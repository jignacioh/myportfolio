package com.udacity.porfolioapp.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.DetailsMovieFragment;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.model.MovieDao;
import com.udacity.porfolioapp.model.Trailer;
import com.udacity.porfolioapp.ui.holder.DetailMovieHolder;
import com.udacity.porfolioapp.ui.holder.TrailerViewHolder;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Juan PC
 */
public class MainMovieRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String BASE_IMAGE="http://image.tmdb.org/t/p/w500/";
    private DetailsMovieFragment detailMovieFragment;
    private DetailsMovieFragment.Callbacks mCallbacks;
    private Context context;
    // The items to display in your RecyclerView
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

        switch (viewType) {
            case DETAIL:
                View v1 = inflater.inflate(R.layout.item_head_movie, viewGroup, false);
                v1.setTag(items);
                viewHolder = new DetailMovieHolder(v1,mCallbacks);
                break;
            case TRAILER:
                View v2 = inflater.inflate(R.layout.item_trailer, viewGroup, false);
                v2.setTag(items);
                viewHolder = new TrailerViewHolder(v2,mCallbacks);
                //break;
            default:
                //View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                //viewHolder = new RecyclerViewSimpleTextViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case DETAIL:
                DetailMovieHolder detailMovieHolder = (DetailMovieHolder) viewHolder;
                fillViewHolderDetail(detailMovieHolder, position);
                break;
            case TRAILER:
                TrailerViewHolder trailerViewHolder = (TrailerViewHolder) viewHolder;
                fillViewHolderTrailer(trailerViewHolder, position);
                break;
            default:
                //RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                //configureDefaultViewHolder(vh, position);
                break;
        }
    }

    private void fillViewHolderTrailer(TrailerViewHolder trailerViewHolder, int position) {
        Trailer trailer=(Trailer)items.get(position);
        if (trailer!=null){
            trailerViewHolder.getTvTrailer().setText("Trailer "+position);

        }
    }

    private void fillViewHolderDetail(DetailMovieHolder detailMovieHolder, int position) {
        Movie movie = (Movie) items.get(position);
        if (movie != null) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                detailMovieHolder.getIvPoster().setTransitionName("profile");
            }
            Glide.with(context).load(BASE_IMAGE+movie.getUrlMovie()).placeholder(R.drawable.placeholder).crossFade().into(  detailMovieHolder.getIvPoster());
            detailMovieHolder.getTvPopularityMovie().setText("Popularity: "+movie.getPopularity());
            detailMovieHolder.getTvRatedMovie().setText("Rated: "+movie.getVoteAverage());
            detailMovieHolder.getTvVotosMovie().setText("Votes: "+movie.getVoteCount());
            detailMovieHolder.getTvYearMovie().setText("Release date: "+movie.getYearMovie());
            detailMovieHolder.getTvSummaryMovie().setText("Summary: "+movie.getDescriptionMovie());
        }
        QueryBuilder<Movie> movieQueryBuilder=detailMovieFragment.getAppDaoSession().getMovieDao().queryBuilder();
        if (movieQueryBuilder.where(MovieDao.Properties.Id.eq(movie.getId())).count()!=0){
            detailMovieHolder.getIvAddFavo().setChecked(true);
        }else {
            detailMovieHolder.getIvAddFavo().setChecked(false);
        }
    }

}