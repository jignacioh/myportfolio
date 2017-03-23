package com.udacity.porfolioapp.ui.holder;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.ListMoviesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jhurtace on 23/03/2017.
 */
public class CursorMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private View layoutView;
    private ListMoviesFragment.Callbacks mCallbacks;
    @BindView(R.id.tvNameMovie)
    TextView tvNameMovie;
    @BindView(R.id.ivImageMovie)
    ImageView ivImageMovie;

    public CursorMovieViewHolder(View layoutView, ListMoviesFragment.Callbacks mCallbacks) {
        super(layoutView);
        ButterKnife.bind(this, layoutView);
        this.layoutView=layoutView;
        this.mCallbacks=mCallbacks;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mCallbacks.onFavoriteSelected((Cursor)layoutView.getTag(),getAdapterPosition(), layoutView.findViewById(R.id.ivImageMovie));
    }

    public TextView getTvNameMovie() {
        return tvNameMovie;
    }

    public void setTvNameMovie(TextView tvNameMovie) {
        this.tvNameMovie = tvNameMovie;
    }

    public ImageView getIvImageMovie() {
        return ivImageMovie;
    }

    public void setIvImageMovie(ImageView ivImageMovie) {
        this.ivImageMovie = ivImageMovie;
    }
}
